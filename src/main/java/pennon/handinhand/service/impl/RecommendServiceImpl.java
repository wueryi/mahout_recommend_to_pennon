package pennon.handinhand.service.impl;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pennon.handinhand.bo.NewsRecommendSource;
import pennon.handinhand.bo.PatternEnum;
import pennon.handinhand.dao.NewsMapper;
import pennon.handinhand.dao.PreferenceMapper;
import pennon.handinhand.dao.RatingsMapper;
import pennon.handinhand.entity.*;
import pennon.handinhand.exception.BusinessException;
import pennon.handinhand.service.RecommendService;
import pennon.handinhand.util.CommonUtil;
import pennon.handinhand.util.DateUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("RecommendMqService")
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private PreferenceMapper preferenceMapper;

    @Override
    public List<JSON> show(long userId, int page) {
        //返回结果
        List<JSON> list = new ArrayList<>();
        //获取当前用户id的推荐结果
        Set<String> user = redisTemplate.opsForZSet().reverseRangeByScore("Recommend:user-" + userId, 0, 5, page * 4 - 4, 4);
        //不存在推荐获取热点数据
        if (user == null || user.isEmpty()) {
            Set<String> hot = redisTemplate.opsForZSet().reverseRangeByScore("Recommend:hot", 0, 5, page * 4 - 4, 4);
            if (hot == null) {
                throw new BusinessException("热点数据为空");
            }
            for (String str : hot) {
                list.add(JSONUtil.parse(str));
            }
        }
        for (String str : user) {
            list.add(JSONUtil.parse(str));
        }
        //推送获取推荐队列
        dispatch(userId);
        return list;
    }

    @Override
    public List<News> updateHot() {
        NewsExample newsExample = new NewsExample();
        newsExample.setOrderByClause("click DESC");
        RowBounds rowBounds = new RowBounds(1, 20);
        List<News> newsList = this.newsMapper.selectByExampleWithRowbounds(newsExample, rowBounds);
        redisTemplate.opsForZSet().removeRange("Recommend:hot", 0, 19);

        for (News news : newsList) {
            redisTemplate.opsForZSet().add("Recommend:hot", JSONUtil.toJsonStr(news), news.getClick());
        }
        return newsList;
    }

    @Override
    public void dataReload() {
        try {
            PreferenceExample preferenceExample = new PreferenceExample();
            PreferenceExample.Criteria criteria = preferenceExample.createCriteria();
            criteria.andIsUpdatedEqualTo(0);
            RowBounds rowBounds = new RowBounds(0, 200);
            List<Preference> preferenceList = this.preferenceMapper.selectByExampleWithRowbounds(preferenceExample, rowBounds);
            if (preferenceList.size() < 200) {
                throw new BusinessException("同步内容数量不足");
            }

            CsvWriter writer = CsvUtil.getWriter(CommonUtil.getBootPath() + "data/ratings." + DateUtil.currentTimestamp() + ".csv", CharsetUtil.CHARSET_UTF_8);
            for (Preference preference : preferenceList) {
                if (preference != null) {
                    writer.write(new String[]{preference.getUserId().toString(), preference.getItemId().toString(), preference.getPreference().toString(), preference.getUpdatedAt().toString()});
                }
            }

            //设置已同步
            Preference preference = new Preference();
            preference.setIsUpdated(1);
            preference.setUpdatedAt(DateUtil.currentTimestamp());
            List<Integer> preferenceIdList = preferenceList.stream().map(Preference::getId).collect(Collectors.toList());
            criteria.andIdIn(preferenceIdList);
            this.preferenceMapper.updateByExampleSelective(preference, preferenceExample);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dispatch(long userId) {
        try {
            redisTemplate.convertAndSend(PatternEnum.UPDATE_USER_RECOMMEND.getName(), String.valueOf(userId));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void handle(long userId) {
        try {
            //通过单例数据源进行推荐
            Recommender recommender = NewsRecommendSource.getInstance().getRecommend();
            List<RecommendedItem> recommendedItemList = recommender.recommend(userId, 20);
            System.out.println("使用基于用户的协同过滤算法，为用户推荐" + recommendedItemList.size() + "个商品");
            List<Long> list = recommendedItemList.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
            if (list.isEmpty()) {
                throw new BusinessException("推荐引擎结果为空");
            }

            NewsExample newsExample = new NewsExample();
            NewsExample.Criteria criteria = newsExample.createCriteria();
            criteria.andIdIn(list);
            List<News> newsList = this.newsMapper.selectByExample(newsExample);
            System.out.println(newsList.size());
            if (newsList.isEmpty()) {
                throw new BusinessException("推荐新闻查询为空");
            }
            Map<Integer, News> newsMap = newsList.stream().collect(Collectors.toMap(News::getId, Function.identity(), (key1, key2) -> key2));

            //重置推荐结果
            redisTemplate.opsForZSet().removeRange("Recommend:user-" + userId, 0, 19);
            for (RecommendedItem recommendedItem : recommendedItemList) {
                News news = newsMap.get((int) recommendedItem.getItemID());
                if (news != null) {
                    redisTemplate.opsForZSet().add("Recommend:user-" + userId, JSONUtil.toJsonStr(news), recommendedItem.getValue());
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

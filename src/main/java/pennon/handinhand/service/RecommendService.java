package pennon.handinhand.service;

import cn.hutool.json.JSON;
import pennon.handinhand.entity.News;

import java.util.List;

public interface RecommendService extends RecommendMq{
    List<JSON> show(long userId, int page);

    List<News> updateHot();

    void dataReload();
}

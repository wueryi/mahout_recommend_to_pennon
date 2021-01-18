package pennon.handinhand.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pennon.handinhand.dao.BannerMapper;
import pennon.handinhand.entity.Banner;
import pennon.handinhand.entity.BannerExample;
import pennon.handinhand.service.BannerService;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> index() {
        BannerExample bannerExample = new BannerExample();
        bannerExample.setOrderByClause("listorder asc");
        BannerExample.Criteria criteria = bannerExample.createCriteria();
        //查询status为
        criteria.andStatusEqualTo(Byte.parseByte("99"));
        return bannerMapper.selectByExample(bannerExample);
    }
}

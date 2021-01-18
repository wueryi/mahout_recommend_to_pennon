package pennon.handinhand.bo;

import org.springframework.beans.factory.annotation.Autowired;
import pennon.handinhand.component.SpringContextHolder;
import pennon.handinhand.service.RecommendService;
import pennon.handinhand.util.DateUtil;

public class PreferenceUpdateJob {
    public void run(){
        System.out.println(DateUtil.currentTimestamp());
        RecommendService recommendService = SpringContextHolder.getBean("RecommendMqService");
        recommendService.dataReload();
    }
}

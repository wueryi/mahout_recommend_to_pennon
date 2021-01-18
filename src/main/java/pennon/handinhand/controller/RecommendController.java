package pennon.handinhand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pennon.handinhand.bo.NewsRecommendSource;
import pennon.handinhand.bo.PreferenceDate;
import pennon.handinhand.bo.Response;
import pennon.handinhand.bo.ResponseEnum;
import pennon.handinhand.dto.RequestRecommendShow;
import pennon.handinhand.dto.RequestUpdatePreference;
import pennon.handinhand.service.PreferenceService;
import pennon.handinhand.service.RecommendService;
import pennon.handinhand.util.ResponseUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;

    @Autowired
    private PreferenceService preferenceService;

    @RequestMapping(value = "/show")
    public ResponseEntity<Response> show(@Valid @RequestBody RequestRecommendShow params) {
        return ResponseUtil.respondWithSuccess(recommendService.show(Long.parseLong(params.getUserId()),Integer.parseInt(params.getPage())));
    }

    @RequestMapping(value = "update-hot")
    public ResponseEntity<Response> updateHot(){
        return ResponseUtil.respondWithSuccess(recommendService.updateHot());
    }

    @RequestMapping(value = "/update-data")
    public void dataReload(){
        this.recommendService.dataReload();
        NewsRecommendSource.getInstance().refresh();
    }

    @RequestMapping(value = "update-preference")
    public ResponseEntity<Response> preferenceReload(@Valid @RequestBody RequestUpdatePreference params){
        int type = Integer.parseInt(params.getType());
        PreferenceDate preference = new PreferenceDate();
        preference.setUserId(Integer.parseInt(params.getUserId()));
        preference.setItemId(Integer.parseInt(params.getItemId()));
        preference.setTimes(Integer.parseInt(params.getTimes()));
        this.preferenceService.compute(type,preference);
        return ResponseUtil.respondWithSuccess(ResponseEnum.SUCCESS);
    }
}

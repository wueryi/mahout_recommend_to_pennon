package pennon.handinhand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pennon.handinhand.bo.Response;
import pennon.handinhand.service.BannerService;
import pennon.handinhand.util.ResponseUtil;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("/index")
    public ResponseEntity<Response> index(){
        return ResponseUtil.respondWithSuccess(bannerService.index());
    }
}

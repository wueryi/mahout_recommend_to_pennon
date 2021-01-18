package pennon.handinhand;

import cn.hutool.cron.CronUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("pennon.handinhand.dao")
public class HandinhandApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandinhandApplication.class, args);
//        CronUtil.setMatchSecond(true);
//        CronUtil.start();
    }

}

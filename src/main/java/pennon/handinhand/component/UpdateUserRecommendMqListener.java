package pennon.handinhand.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import pennon.handinhand.service.RecommendService;

/**
 * @author jishuai
 */
@Component
public class UpdateUserRecommendMqListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("从消息通道={" + new String(pattern) + "}监听到消息");
        System.out.println("消息={" + new String(message.getBody()) + "}");
        ObjectMapper mapper = new ObjectMapper();
        try {
            RecommendService recommendService = SpringContextHolder.getBean("RecommendMqService");
            recommendService.handle(Long.parseLong(new String(message.getBody())));
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}

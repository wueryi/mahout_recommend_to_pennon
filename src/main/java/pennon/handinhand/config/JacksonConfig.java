package pennon.handinhand.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author Administrator
 */
@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                //设置返回时间戳
                jacksonObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");

                //为NULL或者为空不参加序列化
//                jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);

                jacksonObjectMapperBuilder.failOnUnknownProperties(false);

                //返回值转换为下划线类型
                jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

                jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            }
        };
    }


}

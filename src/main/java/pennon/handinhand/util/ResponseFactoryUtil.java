package pennon.handinhand.util;

import pennon.handinhand.bo.Response;
import pennon.handinhand.bo.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author jishuai
 */
public class ResponseFactoryUtil {
    private Response response;

    public ResponseFactoryUtil() {
        this.response = new Response();
        this.response.setHttpStatus(ResponseEnum.SUCCESS.getHttpStatus());
        this.response.setCode(ResponseEnum.SUCCESS.getCode());
        this.response.setMessage(ResponseEnum.SUCCESS.getMessage());
        this.response.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        this.response.setData(null);
    }

    public ResponseFactoryUtil responseEnum(ResponseEnum responseEnum) {
        this.response.setHttpStatus(responseEnum.getHttpStatus());
        this.response.setCode(responseEnum.getCode());
        this.response.setMessage(responseEnum.getMessage());
        this.response.setSuccess(responseEnum.isSuccess());
        this.response.setData(null);
        return this;
    }

    public ResponseFactoryUtil httpStatus(HttpStatus httpStatus) {
        this.response.setHttpStatus(httpStatus);
        return this;
    }

    public ResponseFactoryUtil code(int code) {
        this.response.setCode(code);
        return this;
    }

    public ResponseFactoryUtil message(String message) {
        this.response.setMessage(message);
        return this;
    }

    public ResponseFactoryUtil success(boolean success) {
        this.response.setSuccess(success);
        return this;
    }

    public <T> ResponseFactoryUtil data(T t) {
        this.response.setData(t);
        return this;
    }

    public ResponseEntity<Response> respond() {
        return new ResponseEntity<>(this.response, this.response.getHttpStatus());
    }
}

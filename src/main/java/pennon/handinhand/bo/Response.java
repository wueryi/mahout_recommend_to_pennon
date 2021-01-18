package pennon.handinhand.bo;

import org.springframework.http.HttpStatus;

/**
 * @  httpStatus http状态码
 * @ success 业务状态
 * @ code 业务状态码
 * @ message 消息提示
 * @ T 业务数据
 */
public class  Response<T> {
    private HttpStatus httpStatus;
    private boolean success;
    private int code;
    private String message;
    private T data;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

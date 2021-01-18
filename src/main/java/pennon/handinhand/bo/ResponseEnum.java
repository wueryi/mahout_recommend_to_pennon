package pennon.handinhand.bo;

import org.springframework.http.HttpStatus;

public enum ResponseEnum {
    //成功返回的状态码
    SUCCESS(HttpStatus.OK, true, 0, "操作成功"),
    CREATED(HttpStatus.CREATED, true, 0, "创建成功"),
    UPDATED(HttpStatus.OK, true, 0, "更新成功"),
    DELETED(HttpStatus.NO_CONTENT, true, 0, "删除成功"),

    //错误返回状态
    ERROR(HttpStatus.OK, false, 10001, "未知错误"),
    NOT_FOUND(HttpStatus.NOT_FOUND, false, 10002, "文件不存在或者文件不可读或者文件是目录"),
    IO_ERROR(HttpStatus.OK, false, 10003, "读取过程存在异常"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, false, 10004, "token失效"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, 10005, "请求参数错误"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, false, 10006, "http请求方法不正确");

    private HttpStatus httpStatus;
    private boolean success;
    private int code;
    private String message;

    ResponseEnum(HttpStatus httpStatus, boolean success, int code, String message) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

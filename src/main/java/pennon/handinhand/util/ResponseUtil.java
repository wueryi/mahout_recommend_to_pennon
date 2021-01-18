package pennon.handinhand.util;

import pennon.handinhand.bo.Response;
import pennon.handinhand.bo.ResponseEnum;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {
    private static <T> ResponseEntity<Response> respond(ResponseEnum responseEnum, String message, T t) {
        Response response = new Response();
        response.setHttpStatus(responseEnum.getHttpStatus());
        response.setSuccess(responseEnum.isSuccess());
        response.setCode(responseEnum.getCode());
        if (message.isEmpty()) {
            response.setMessage(responseEnum.getMessage());
        } else {
            response.setMessage(message);
        }
        response.setData(t);
        return new ResponseEntity<>(response, responseEnum.getHttpStatus());
    }

    public static <T> ResponseEntity<Response> respondWithSuccess(T t) {
        ResponseEnum responseEnum = ResponseEnum.SUCCESS;
        return respond(responseEnum, "", t);
    }

    public static <T> ResponseEntity<Response> respondWithSuccess(String message, T t) {
        ResponseEnum responseEnum = ResponseEnum.SUCCESS;
        return respond(responseEnum, message, t);
    }

    public static <T> ResponseEntity<Response> respondWithSuccess(ResponseEnum responseEnum, T t) {
        return respond(responseEnum, responseEnum.getMessage(), t);
    }

    public static ResponseEntity<Response> respondWithError() {
        ResponseEnum responseEnum = ResponseEnum.ERROR;
        return respond(responseEnum, "", null);
    }

    public static ResponseEntity<Response> respondWithError(ResponseEnum responseEnum) {
        return respond(responseEnum, responseEnum.getMessage(), null);
    }

    public static ResponseEntity<Response> respondWithError(String message) {
        ResponseEnum responseEnum = ResponseEnum.ERROR;
        return respond(responseEnum, message, null);
    }
}

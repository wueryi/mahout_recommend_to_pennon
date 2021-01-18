package pennon.handinhand.exception;

/**
 * @author jishuai
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}

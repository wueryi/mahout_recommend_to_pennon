package pennon.handinhand.exception.handler;

import pennon.handinhand.bo.Response;
import pennon.handinhand.bo.ResponseEnum;
import pennon.handinhand.exception.BusinessException;
import pennon.handinhand.exception.MethodNotAllowedException;
import pennon.handinhand.exception.NotFoundException;
import pennon.handinhand.exception.UnauthorizedException;
import pennon.handinhand.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.List;

/**
 * @author jishuai
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handBusinessException(BusinessException exception){
        return ResponseUtil.respondWithError(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handNotFoundException(NotFoundException exception){
        return ResponseUtil.respondWithError(ResponseEnum.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handRuntimeException(RuntimeException exception){
        return ResponseUtil.respondWithError(exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Response> handIOException(IOException exception){
        return ResponseUtil.respondWithError(ResponseEnum.IO_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response> handUnauthorizedException(UnauthorizedException exception){
        return ResponseUtil.respondWithError(ResponseEnum.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<Response> handMethodNotAllowedException(MethodNotAllowedException exception){
        return ResponseUtil.respondWithError(ResponseEnum.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            stringBuilder.append( error.getDefaultMessage()+"\n");
        }
        return ResponseUtil.respondWithError("方法参数验证未通过:"+stringBuilder);
    }
}
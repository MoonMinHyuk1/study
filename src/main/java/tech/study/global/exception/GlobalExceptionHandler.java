package tech.study.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.study.global.response.ApplicationResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationResponse<ErrorCode>> applicationException(ApplicationException e) {
        return ResponseEntity.status(e.errorCode.getStatus()).body(new ApplicationResponse(e.errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationResponse<ErrorCode>> validationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApplicationResponse(ErrorCode.INVALID_VALUE_EXCEPTION,
                        e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApplicationResponse<ErrorCode>> runtimeException(RuntimeException e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ApplicationResponse(ErrorCode.INTERNAL_SERVER_EXCEPTION));
//    }
}

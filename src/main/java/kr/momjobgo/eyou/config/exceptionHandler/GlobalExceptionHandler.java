package kr.momjobgo.eyou.config.exceptionHandler;

import kr.momjobgo.eyou.config.exceptionHandler.exception.NoDataException;
import kr.momjobgo.eyou.config.exceptionHandler.exception.UnauthenticationException;
import kr.momjobgo.eyou.config.exceptionHandler.vo.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice(basePackages = "kr.momjobgo.eyou")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e){
        log.error("", e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(e.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(e.getFieldError().getDefaultMessage())
                .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = UnauthenticationException.class)
    public ResponseEntity<ErrorResponse> handleUnauthenticationException(Exception e){
        log.error("", e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(e.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = NoDataException.class)
    public ResponseEntity<ErrorResponse> handleNoDataException(Exception e){
        log.error("", e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

}

package es.urjc.code.s3_ejer1;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RestControllerExceptionHandler
 */
@ControllerAdvice
public class RestControllerExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(com.amazonaws.services.s3.model.AmazonS3Exception.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                                    ex.getMessage(),
                                    new HttpHeaders(),
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    request);
    }
}
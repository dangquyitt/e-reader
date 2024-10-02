package utc2.itk62.e_reader.exception.handler;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.exception.CustomException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<HTTPResponse> handleCustomException(CustomException ce) {
        return ResponseEntity.status(ce.getStatus()).body(new HTTPResponse("error", ce.getErrors()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HTTPResponse> handleException(Exception e) {
        // TODO: Logging error to debug here
        return ResponseEntity.status(500).body(new HTTPResponse("error", "internal server error"));
    }
}

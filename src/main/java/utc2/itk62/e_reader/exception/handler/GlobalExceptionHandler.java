package utc2.itk62.e_reader.exception.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.exception.AuthenticationException;
import utc2.itk62.e_reader.exception.DuplicateException;
import utc2.itk62.e_reader.exception.NotFoundException;


@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {
    private final Translator translator;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HTTPResponse> handleException(Exception ex) {
        log.error("Exception | {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HTTPResponse.builder()
                        .message(translator.translate(MessageCode.ERROR_INTERNAL_SERVER))
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HTTPResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException | {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(HTTPResponse.builder()
                        .message(translator.translate(MessageCode.ERROR_ARGUMENTS_INVALID))
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HTTPResponse> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(HTTPResponse.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<HTTPResponse> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(HTTPResponse.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<HTTPResponse> handleDuplicateException(DuplicateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(HTTPResponse.builder()
                        .message(ex.getMessage())
                        .build());
    }
}

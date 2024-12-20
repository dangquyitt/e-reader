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
import utc2.itk62.e_reader.exception.EReaderException;

import java.util.Locale;


@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {
    private final Translator translator;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HTTPResponse> handleException(Exception ex, Locale locale) {
        log.error("Exception | {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HTTPResponse.builder()
                        .message(translator.translate(locale, MessageCode.ERROR_INTERNAL_SERVER))
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HTTPResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        log.error("MethodArgumentNotValidException | {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(HTTPResponse.builder()
                        .message(translator.translate(locale, MessageCode.ERROR_ARGUMENTS_INVALID))
                        .build());
    }

    @ExceptionHandler(EReaderException.class)
    public ResponseEntity<HTTPResponse> handleEReaderException(EReaderException ex, Locale locale) {
        log.error("EReaderException | {}", ex.getCode());
        return ResponseEntity.badRequest()
                .body(HTTPResponse.builder()
                        .code(ex.getCode())
                        .message(translator.translate(locale, ex.getCode(), ex.getArgs()))
                        .build());
    }
}

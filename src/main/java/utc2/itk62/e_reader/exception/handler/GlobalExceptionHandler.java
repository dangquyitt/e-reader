package utc2.itk62.e_reader.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.core.response.ErrorResponse;
import utc2.itk62.e_reader.exception.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        if (ex.getException() != null) {
            log.error(ex.getException().getMessage(), ex);
        }
        if (ex.getInternalMessage() != null) {
           log.error(ex.getInternalMessage(), ex);
        }
        Locale locale = LocaleContextHolder.getLocale();
        List<Error> errors = new ArrayList<>();
        ex.getErrors().forEach(error -> {
            errors.add(new Error(error.getField(), messageSource.getMessage(error.getMessage(), null, locale)));
        });
        return ErrorResponse.badRequest(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ErrorResponse.internalServerError();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(new Error(fieldError.getField(), messageSource.getMessage(Objects.requireNonNull(fieldError.getDefaultMessage()), null, locale)));
        }
        return ErrorResponse.badRequest(errors);
    }
}

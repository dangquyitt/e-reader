package utc2.itk62.e_reader.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.exception.*;

import java.util.*;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<HTTPResponse> handleException(Exception ex, Locale locale) {
        String message = messageSource.getMessage("error.internal_server_error", null, locale);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HTTPResponse.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<HTTPResponse> handleForbiddenException(ForbiddenException ex, Locale locale) {
        String message = messageSource.getMessage("error.forbidden", null, locale);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(HTTPResponse.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<HTTPResponse> handleUnauthorizedException(UnauthorizedException ex, Locale locale) {
        String message = messageSource.getMessage("error.unauthorized", null, locale);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(HTTPResponse.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HTTPResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        Map<String, Error> fieldErrorMap = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (fieldError.getDefaultMessage() != null)
                fieldErrorMap.put(fieldError.getField(), new Error(messageSource.getMessage(fieldError.getDefaultMessage(), fieldError.getArguments(), locale)));
        }
        return ResponseEntity.badRequest()
                .body(HTTPResponse.builder()
                        .errors(fieldErrorMap)
                        .build());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<HTTPResponse> handleDuplicateException(DuplicateException ex, Locale locale) {
        Map<String, Error> fieldErrorMap = new HashMap<>();
        String message = messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale);
        fieldErrorMap.put(ex.getField(), new Error(message));
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(HTTPResponse.builder()
                        .errors(fieldErrorMap)
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HTTPResponse> handleNotFoundException(NotFoundException ex, Locale locale) {
        Map<String, Error> fieldErrorMap = new HashMap<>();
        String message = messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale);
        fieldErrorMap.put(ex.getField(), new Error(message));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(HTTPResponse.builder()
                        .errors(fieldErrorMap)
                        .build());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<HTTPResponse> handleInvalidCredentialException(InvalidCredentialException ex, Locale locale) {
        String message = messageSource.getMessage("credential.invalid", null, locale);
        return ResponseEntity.badRequest()
                .body(HTTPResponse.builder()
                        .message(message)
                        .build());
    }
}

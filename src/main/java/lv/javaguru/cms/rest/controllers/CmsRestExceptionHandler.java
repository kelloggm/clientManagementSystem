package lv.javaguru.cms.rest.controllers;

import com.google.common.collect.Lists;
import lv.javaguru.cms.rest.CmsError;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CmsRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {SecurityException.class})
    protected ResponseEntity<Object> handleSecurityException(SecurityException ex, WebRequest request) {
        List<CmsError> errors = Lists.newArrayList(CmsError.builder()
                .category(CmsErrorCategory.WORKFLOW)
                .code(CmsErrorCode.UNAUTHORIZED)
                .description(ex.getMessage())
                .build());
        Map<String, Object> body = buildResponseBody(errors);
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.valueOf(retrieveHttpCode(errors)));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleConflict(IllegalArgumentException ex, WebRequest request) {
        List<CmsError> errors = Lists.newArrayList(CmsError.builder()
                .category(CmsErrorCategory.VALIDATION)
                .code(CmsErrorCode.INVALID_FIELD_VALUE)
                .description(ex.getMessage())
                .build());
        Map<String, Object> body = buildResponseBody(errors);
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.valueOf(retrieveHttpCode(errors)));
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<CmsError> errors = retrieveErrors(ex);
        Map<String, Object> body = buildResponseBody(errors);
        return new ResponseEntity<>(body, headers, HttpStatus.valueOf(retrieveHttpCode(errors)));
    }

    private List<CmsError> retrieveErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(this::transform)
                    .collect(Collectors.toList());
    }

    private Map<String, Object> buildResponseBody(List<CmsError> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("errors", errors);
        return body;
    }

    private int retrieveHttpCode(List<CmsError> errors) {
        return 200;
    }

    private CmsError transform(FieldError fieldError) {
        if (fieldError.getRejectedValue() != null) {
            return CmsError.builder()
                    .category(CmsErrorCategory.VALIDATION)
                    .code(CmsErrorCode.INVALID_FIELD_VALUE)
                    .description(fieldError.getField() + " = " + fieldError.getRejectedValue())
                    .build();
        } else {
            return CmsError.builder()
                    .category(CmsErrorCategory.VALIDATION)
                    .code(CmsErrorCode.MISSING_FIELD)
                    .description(fieldError.getField())
                    .build();
        }
    }

}

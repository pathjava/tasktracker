package ru.progwards.tasktracker.exception.exceptioncontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * @author Oleg Kiselev
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            HttpStatus status, @NonNull WebRequest request) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("timestamp", new Date());
        map.put("status", status.value());

        List<Map<String, List<String>>> allErrors = new ArrayList<>();
        Map<String, List<String>> fieldErrors = new LinkedHashMap<>();
        List<String> errorReason;
        for (ObjectError allError : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) allError).getField();
            if (fieldErrors.containsKey(fieldName))
                errorReason = fieldErrors.get(fieldName);
            else
                errorReason = new ArrayList<>();
            errorReason.add(allError.getDefaultMessage());
            fieldErrors.put(fieldName, errorReason);
        }
        allErrors.add(fieldErrors);
        map.put("errors", allErrors);

        return new ResponseEntity<>(map, headers, status);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("timestamp", new Date());
        map.put("status", HttpStatus.BAD_REQUEST.value());

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        map.put("errors", errors);

        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}

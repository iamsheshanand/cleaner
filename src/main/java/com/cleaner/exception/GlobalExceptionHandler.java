package com.cleaner.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> invalidMethodArgumentHandleException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("details", errors);
        log.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WindowServiceException.class)
    public ResponseEntity<Error> windowServiceException(WindowServiceException windowServiceException) {
        Error error = new Error(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), windowServiceException.getLocalizedMessage());
        log.error(error.toString());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> runtimeException(RuntimeException runtimeException) {
        Error error = new Error(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), runtimeException.getLocalizedMessage());
        log.error(error.toString());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exception(Exception exception) {
        Error error = new Error(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
        log.error(error.toString());
        return ResponseEntity.badRequest().body(error);
    }
}

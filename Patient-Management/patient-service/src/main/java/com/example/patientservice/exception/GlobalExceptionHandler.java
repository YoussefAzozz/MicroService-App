package com.example.patientservice.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(
            GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);

    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<Map<String, String>> handleEmailDuplication(
            EmailAlreadyExists ex){
        log.warn(ex.getMessage());
//        log.warn("Email address already exists");
        Map<String, String> errors = new HashMap<>();
        errors.put("message","Email address already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleIdNotFound(
            PatientNotFoundException ex){
        log.warn(ex.getMessage());
//        log.warn("Email address already exists");
        Map<String, String> errors = new HashMap<>();
        errors.put("message","Patient Not Found");
        return ResponseEntity.badRequest().body(errors);
    }

}

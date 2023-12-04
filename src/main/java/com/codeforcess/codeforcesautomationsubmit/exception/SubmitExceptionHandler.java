package com.codeforcess.codeforcesautomationsubmit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class SubmitExceptionHandler {

    @ExceptionHandler(SubmitException.class)
    public ResponseEntity<?> submitException(String message, WebRequest webRequest) {
        ExceptionMessage errorDetails = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                message,
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(LocalDateTime.now()),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(String message, WebRequest webRequest) {
        ExceptionMessage errorDetails = new ExceptionMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message,
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(LocalDateTime.now()),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

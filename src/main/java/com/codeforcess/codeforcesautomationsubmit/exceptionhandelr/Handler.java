package com.codeforcess.codeforcesautomationsubmit.exceptionhandelr;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(SubmitException.class)
    public ResponseEntity<?> submitEx(String message , WebRequest webRequest){
        Message errorDetails = new Message(
                HttpStatus.NO_CONTENT.value() ,
                message ,
                new Date() ,
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails , HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(String message , WebRequest webRequest){
        Message errorDetails = new Message(
                HttpStatus.INTERNAL_SERVER_ERROR.value() ,
                message ,
                new Date() ,
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

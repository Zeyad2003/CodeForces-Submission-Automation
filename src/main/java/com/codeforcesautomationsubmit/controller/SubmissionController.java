package com.codeforcesautomationsubmit.controller;

import com.codeforcesautomationsubmit.service.CodeforcesAutomationImpl;
import com.codeforcesautomationsubmit.models.SubmissionInfo;
import com.codeforcesautomationsubmit.models.SubmissionResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class SubmissionController {
    private final CodeforcesAutomationImpl service;

    @GetMapping
    public String welcomeMessage() {
        return "HELLO, THE SERVER IS WORKING :)";
    }

    @PostMapping("/submit/{problemCode}")
    ResponseEntity<SubmissionResult> submit(@PathVariable String problemCode, @RequestBody @Valid SubmissionInfo data) {
        return new ResponseEntity<>(service.submit(problemCode, data), HttpStatus.OK);
    }
}

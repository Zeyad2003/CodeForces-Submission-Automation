package com.codeforcesautomationsubmit.controller;

import com.codeforcesautomationsubmit.service.CodeforcesAutoService;
import com.codeforcesautomationsubmit.models.ProblemSubmitData;
import com.codeforcesautomationsubmit.models.ProblemSubmitResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class SubmissionController {
    private final CodeforcesAutoService service;

    @GetMapping
    public String welcomeMessage() {
        return "HELLO, THE SERVER IS WORKING :)";
    }

    @PostMapping("/submit/{problemCode}")
    ResponseEntity<ProblemSubmitResult> submit(@PathVariable String problemCode, @RequestBody ProblemSubmitData data) {
        return new ResponseEntity<>(service.submit(problemCode, data), HttpStatus.OK);
    }
}

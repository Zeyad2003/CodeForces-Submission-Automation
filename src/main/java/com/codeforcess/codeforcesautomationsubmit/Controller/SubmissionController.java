package com.codeforcess.codeforcesautomationsubmit.Controller;


import com.codeforcess.codeforcesautomationsubmit.Services.CodeforcesAutoService;
import com.codeforcess.codeforcesautomationsubmit.models.ProblemSubmitData;
import com.codeforcess.codeforcesautomationsubmit.models.ProblemSubmitRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SubmissionController {
    CodeforcesAutoService service;

    @Autowired
    public SubmissionController(CodeforcesAutoService service){
        this.service = service;
    }

    @GetMapping
    public String getSer(){
        return "SERVER WORKING";
    }

    @PostMapping("/submit/{problemCode}")
    ResponseEntity<ProblemSubmitRes> submit(@PathVariable String problemCode , @RequestBody ProblemSubmitData data){
        return new ResponseEntity<>(service.submit(problemCode , data) , HttpStatus.OK);
    }

}

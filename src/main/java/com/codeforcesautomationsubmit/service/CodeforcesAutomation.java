package com.codeforcesautomationsubmit.service;

import com.codeforcesautomationsubmit.models.SubmissionInfo;
import com.codeforcesautomationsubmit.models.SubmissionResult;

public interface CodeforcesAutomation {
    SubmissionResult submit(String problemCode, SubmissionInfo data);

    void login();
}

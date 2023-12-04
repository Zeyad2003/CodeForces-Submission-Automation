package com.codeforcess.codeforcesautomationsubmit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSubmitResult {
    String verdict;

    String time;

    String memory;

    String submitTime;
}

package com.codeforcesautomationsubmit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSubmitData {
    String solutionCode;

    int compilerId;
}

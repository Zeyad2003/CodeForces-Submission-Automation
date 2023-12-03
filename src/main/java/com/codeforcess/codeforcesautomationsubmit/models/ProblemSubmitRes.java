package com.codeforcess.codeforcesautomationsubmit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSubmitRes {
    String verdict;
    String time;
    String memory;
    Date submitTime;
}

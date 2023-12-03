package com.codeforcess.codeforcesautomationsubmit.exceptionhandelr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private int code;
    private String message;
    private Date timeStamp;
    private String desc;
}

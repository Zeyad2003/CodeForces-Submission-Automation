package com.codeforcesautomationsubmit.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionInfo {

    @NotNull
    @NotBlank
    String solutionCode;

    @NotNull
    Integer compilerId;
}

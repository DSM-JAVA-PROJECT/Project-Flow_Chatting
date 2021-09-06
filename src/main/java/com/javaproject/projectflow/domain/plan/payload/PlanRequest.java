package com.javaproject.projectflow.domain.plan.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequest {

    private String planName;

    private LocalDate planStartDate;

    private LocalDate planEndDate;

}

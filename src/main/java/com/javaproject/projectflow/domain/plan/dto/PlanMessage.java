package com.javaproject.projectflow.domain.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanMessage {

    private String planName;

    private LocalDate planEndDate;

    private LocalDate planStartDate;

    private LocalDateTime createdAt;

    private String sender;

}

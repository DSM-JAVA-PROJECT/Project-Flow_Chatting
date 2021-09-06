package com.javaproject.projectflow.domain.plan.dto;

import com.javaproject.projectflow.domain.plan.payload.PlanRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePlanDto {

    private final PlanRequest request;

    private final String userEmail;

    private final String projectId;

    private final String chatRoomId;

}

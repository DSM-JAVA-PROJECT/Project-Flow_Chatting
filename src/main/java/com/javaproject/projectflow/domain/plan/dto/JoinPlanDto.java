package com.javaproject.projectflow.domain.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JoinPlanDto {

    private String projectId;

    private String chatRoomId;

    private String planId;

    private String userId;

}

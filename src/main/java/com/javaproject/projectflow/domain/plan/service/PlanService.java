package com.javaproject.projectflow.domain.plan.service;

import com.javaproject.projectflow.domain.plan.dto.CreatePlanDto;
import com.javaproject.projectflow.domain.plan.dto.JoinPlanDto;
import reactor.core.publisher.Mono;

public interface PlanService {
    Mono<String> createPlan(CreatePlanDto dto);

    Mono<String> joinPlan(JoinPlanDto dto);
}

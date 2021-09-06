package com.javaproject.projectflow.domain.plan.service;

import com.javaproject.projectflow.domain.plan.dto.CreatePlanDto;
import com.javaproject.projectflow.domain.plan.dto.JoinPlanDto;
import com.javaproject.projectflow.domain.plan.dto.PlanMessage;
import com.javaproject.projectflow.domain.plan.entity.Plan;
import com.javaproject.projectflow.domain.plan.entity.PlanRepository;
import com.javaproject.projectflow.domain.user.UserRepository;
import com.javaproject.projectflow.global.jackson.ReactiveObjectMapper;
import com.javaproject.projectflow.global.rabbit.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final ReactiveObjectMapper objectMapper;
    private final Sender sender;

    @Override
    public Mono<String> createPlan(CreatePlanDto dto) {
        return buildPlan(dto)
                .flatMap(plan -> planRepository.save(dto.getProjectId(), dto.getChatRoomId(), plan))
                .flatMap(planId -> planRepository.findPlan(dto.getProjectId(), dto.getChatRoomId(), planId))
                .map(this::buildPlanMessage)
                .flatMap(message -> sendMessage(dto.getChatRoomId(), message))
                .then(Mono.just("success"));
    }

    @Override
    public Mono<String> joinPlan(JoinPlanDto dto) {
        return planRepository.addUser(dto.getProjectId(), dto.getChatRoomId(), dto.getPlanId(), dto.getUserId())
                .then(Mono.just("success"));
    }

    private PlanMessage buildPlanMessage(Plan plan) {
        return PlanMessage.builder()
                .createdAt(plan.getCreatedAt())
                .planEndDate(plan.getEndDate())
                .planName(plan.getName())
                .planStartDate(plan.getStartDate())
                .sender(plan.getSender().getEmail())
                .build();
    }

    private Mono<Plan> buildPlan(CreatePlanDto dto) {
        return userRepository.findByEmail(dto.getUserEmail())
                .map(user -> Plan.builder()
                        .endDate(dto.getRequest().getPlanEndDate())
                        .startDate(dto.getRequest().getPlanEndDate())
                        .name(dto.getRequest().getPlanName())
                        .userIds(List.of(user))
                        .build()
                );
    }

    private Mono<Void> sendMessage(String chatRoomId, PlanMessage message) {
        return objectMapper.encodeValue(PlanMessage.class, Mono.just(message))
                .map(dataBuffer -> Mono.just(new OutboundMessage(RabbitMQConfig.DIRECT_EXCHANGE, chatRoomId, dataBuffer.asByteBuffer().array())))
                .flatMap(sender::send)
                .then();
    }

}

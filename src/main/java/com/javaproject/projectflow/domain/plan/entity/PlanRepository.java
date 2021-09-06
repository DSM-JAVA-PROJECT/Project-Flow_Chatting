package com.javaproject.projectflow.domain.plan.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Repository
public class PlanRepository {
    private final ReactiveMongoTemplate template;

    public Mono<String> save(String projectId, String chatRoomId, Plan plan) {
        return template.updateFirst(query(new Criteria("_id").is(projectId)
                                .elemMatch(new Criteria("_id").is(chatRoomId))),
                        new Update().push("plans", plan),
                        "plans")
                .map(updateResult -> updateResult.getUpsertedId().asString().getValue());
    }

    public Mono<Plan> findPlan(String projectId, String chatRoomId, String planId) {
        return template.findOne(query(new Criteria("_id").is(projectId)
                .elemMatch(new Criteria("_id").is(chatRoomId))
                .elemMatch(new Criteria("_id").is(planId))),
                Plan.class);
    }

}

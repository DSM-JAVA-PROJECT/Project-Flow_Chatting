package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.project.entity.Project;
import com.javaproject.projectflow.domain.project.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<ChatRoom> save(String projectId, ChatRoom chatRoom) {
        Mono<Project> projectMono = mongoTemplate.findOne(Query.query(new Criteria("_id").is(projectId)), Project.class)
                .switchIfEmpty(Mono.error(ProjectNotFoundException.EXCEPTION))
                .doOnError(throwable -> Mono.error(ProjectNotFoundException.EXCEPTION));

        return projectMono
                .map(project -> {
                    project.getChatRooms().add(chatRoom);
                    return project;
                })
                .flatMap(mongoTemplate::save)
                .mapNotNull(project -> project.getChatRooms().stream().findFirst().orElse(null));
    }

    public Flux<ChatRoom> findChatRooms(String projectId) {
        return mongoTemplate.findOne(Query.query(new Criteria("_id").is(projectId)), Project.class)
                .flatMapMany(project -> Flux.fromIterable(project.getChatRooms()));
    }

}

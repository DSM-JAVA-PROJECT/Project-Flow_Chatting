package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.project.entity.Project;
import com.javaproject.projectflow.domain.project.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<ChatRoom> save(String projectId, ChatRoom chatRoom) {
        Mono<Project> projectMono = mongoTemplate.findOne(query(new Criteria("_id").is(projectId)), Project.class)
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

    public Mono<ChatRoom> getChatRoom(String projectId, String chatRoomId) {
        return findChatRooms(projectId)
                .filter(chatRoom -> chatRoom.getId().equals(chatRoomId))
                .singleOrEmpty();
    }

    public Flux<ChatRoom> findChatRooms(String projectId) {
        return mongoTemplate.findOne(query(new Criteria("_id").is(projectId)), Project.class)
                .flatMapMany(project -> Flux.fromIterable(project.getChatRooms()));
    }

}

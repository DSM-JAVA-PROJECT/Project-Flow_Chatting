package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.project.entity.Project;
import com.javaproject.projectflow.domain.project.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<String> save(String projectId, ChatRoom chatRoom) {
        return mongoTemplate.updateFirst(query(new Criteria().is(projectId)),
                        new Update().push("chatRooms", chatRoom),
                        "chatRooms")
                .map(updateResult -> updateResult.getUpsertedId().asString().getValue());
    }

    public Mono<ChatRoom> getChatRoom(String projectId, String chatRoomId) {
        return findChatRooms(projectId)
                .filter(chatRoom -> chatRoom.getId().equals(chatRoomId))
                .singleOrEmpty();
    }

    public Flux<ChatRoom> findChatRooms(String projectId) {
        return findProject(projectId)
                .flatMapMany(project -> Flux.fromIterable(project.getChatRooms()));
    }

    public Mono<String> joinChatRoom(String projectId, String userId) {
        return mongoTemplate.updateFirst(query(new Criteria("_id").is(projectId)),
                        new Update().push("chatRooms").value(userId),
                        "chatRooms")
                .map(updateResult -> updateResult.getUpsertedId().asString().getValue());
    }

    private Mono<Project> findProject(String projectId) {
        return mongoTemplate.findOne(query(new Criteria("_id").is(projectId)), Project.class)
                .switchIfEmpty(Mono.error(ProjectNotFoundException.EXCEPTION))
                .doOnError(throwable -> Mono.error(ProjectNotFoundException.EXCEPTION));
    }

    private ChatRoom getChatRoomFromProject(Project project, String chatRoomId) {
        return Objects.requireNonNull(project.getChatRooms().stream()
                .filter(chatRoom -> chatRoom.getId().equals(chatRoomId))
                .findFirst()
                .orElse(null));
    }

}

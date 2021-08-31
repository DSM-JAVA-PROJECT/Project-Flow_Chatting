package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.project.entity.Project;
import com.javaproject.projectflow.domain.project.entity.ProjectRepository;
import com.javaproject.projectflow.domain.project.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    private final ProjectRepository projectRepository;

    public Mono<ChatRoom> save(String projectId, ChatRoom chatRoom) {
        Mono<Project> projectMono = projectRepository.findById(projectId)
                .switchIfEmpty(Mono.error(ProjectNotFoundException.EXCEPTION));

        return projectMono
                .map(project -> {
                    project.getChatRooms().add(chatRoom);
                    return project;
                })
                .flatMap(projectRepository::save)
                .mapNotNull(project -> project.getChatRooms().stream().findFirst().orElse(null));
    }

}

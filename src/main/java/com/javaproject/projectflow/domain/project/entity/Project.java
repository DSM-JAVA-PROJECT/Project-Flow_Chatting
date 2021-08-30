package com.javaproject.projectflow.domain.project.entity;

import com.javaproject.projectflow.domain.CreatedAtEntity;
import com.javaproject.projectflow.domain.user.User;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document
public class Project extends CreatedAtEntity {

    @MongoId
    private String id;

    @Field("project_name")
    private String projectName;

    @DBRef(lazy = true)
    @Field("user_ids")
    private List<User> userIds;

    @NonNull
    private String title;

    private String subject;

    private String explanation;

    @NonNull
    @Field("start_at")
    private LocalDate startAt;

    @NonNull
    @Field("end_at")
    private LocalDate endAt;

    @Field("logo_image")
    private String logoImage;

}
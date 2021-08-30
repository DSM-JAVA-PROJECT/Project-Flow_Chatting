package com.javaproject.projectflow.domain.user;

import com.javaproject.projectflow.domain.plan.entity.Plan;
import com.javaproject.projectflow.domain.project.entity.Project;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document
public class User {

    @MongoId
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String password;

    @Field("profile_image")
    private String profileImage;

    @Field("phone_number")
    private String phoneNumber;

    @DBRef(lazy = true)
    private List<Project> projects;

    @DBRef(lazy = true)
    private List<Plan> plans;

}

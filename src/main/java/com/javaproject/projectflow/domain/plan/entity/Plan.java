package com.javaproject.projectflow.domain.plan.entity;

import com.javaproject.projectflow.domain.CreatedAtEntity;
import com.javaproject.projectflow.domain.user.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Plan extends CreatedAtEntity {

    @MongoId
    private String id;

    @NonNull
    private String name;

    @NonNull
    @Field("end_date")
    private LocalDate endDate;

    @DBRef
    @Field("user_ids")
    private List<User> userIds;

}
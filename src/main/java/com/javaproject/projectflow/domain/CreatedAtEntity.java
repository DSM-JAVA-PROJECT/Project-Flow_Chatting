package com.javaproject.projectflow.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
public abstract class CreatedAtEntity implements Persistable<String> {      // persistable은 해당 엔티티가 새로운것인지 확인하기 위한 isNew 메소드를 제공한다.

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    public abstract String getId();

    @Override
    public boolean isNew() {
        return getId() == null;
    }
}

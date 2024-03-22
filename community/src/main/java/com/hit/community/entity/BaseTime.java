package com.hit.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false)
    LocalDateTime updatedTime;
}
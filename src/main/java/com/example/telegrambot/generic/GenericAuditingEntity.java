package com.example.telegrambot.generic;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class GenericAuditingEntity {
    @CreatedBy
    @Column(name = "created_by",updatable = false)
    private UUID createdBy;

    @CreatedDate
    @Column(name = "created_date",nullable = false)
    private Long createdDate;

    @LastModifiedDate
    @Column(name = "modified_date",nullable = false)
    private Long modifiedDate;

    @LastModifiedBy
    @Column(name = "modified_by")
    private UUID modifiedBy;


    @Column(name = "deleted", columnDefinition = " boolean DEFAULT false")
    private boolean deleted = false;
}

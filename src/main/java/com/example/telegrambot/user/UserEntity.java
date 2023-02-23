package com.example.telegrambot.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(unique = true)
    private UUID id;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    private String hashPassword;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DataStatusEnum status;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleNameEnum role;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private UUID createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private Long createdDate;

    @LastModifiedDate
    @Column(name = "modified_date", nullable = false)
    private Long modifiedDate;

    @LastModifiedBy
    @Column(name = "modified_by")
    private UUID modifiedBy;


}

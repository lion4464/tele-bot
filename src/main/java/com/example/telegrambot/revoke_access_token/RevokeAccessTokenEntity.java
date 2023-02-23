package com.example.telegrambot.revoke_access_token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "revoke_access_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class RevokeAccessTokenEntity {

    @Id
    @Column(name = "access_token", unique = true)
    private String accessToken;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private Long createdDate;


    @Column(name = "user_id")
    private UUID userId;


    public RevokeAccessTokenEntity(String accessToken, UUID userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
    public String getId() {
        return accessToken;
    }


    public boolean isDeleted() {
        return false;
    }
}

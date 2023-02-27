package com.example.telegrambot.joined_chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JoinedChatRepository extends JpaRepository<JoinedChatEntity, UUID> {
    List<JoinedChatEntity> findAllByCreatedBy(UUID id);

    List<JoinedChatEntity> findAllByCreatedByAndStatusAndDeletedFalse(UUID userId, JoinedChatStatus status);
}

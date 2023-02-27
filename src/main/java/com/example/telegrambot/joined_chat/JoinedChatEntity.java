package com.example.telegrambot.joined_chat;

import com.example.telegrambot.bot.Message;
import com.example.telegrambot.generic.GenericAuditingEntity;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "joined_chat", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"chat_id", "created_by"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLDelete(sql = "update joined_chat set deleted = 'true' where id = ?")
@Where(clause = "deleted = 'false'")
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class JoinedChatEntity extends GenericAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;


    @Column(name = "name",nullable = false)
    private String title;


    @Column(name = "chat_id",nullable = false)
    private String chatId;

    @Type(type = "jsonb")
    @Column(name = "my_chat_member",  columnDefinition = "jsonb", nullable = false)
    private Map<String,Object> myChatMember;

    @Column(name = "type")
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JoinedChatStatus status = JoinedChatStatus.ACTIVE;



}

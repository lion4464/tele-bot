package com.example.telegrambot.file;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLDelete(sql = "update file set deleted = 'true' where id = ?")
@Where(clause = "deleted = 'false'")
@EntityListeners(AuditingEntityListener.class)
public class FileEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private UUID createdBy;

    @CreatedDate
    @Column(name = "created_date",nullable = false)
    private Long createdDate;

    @LastModifiedDate
    @Column(name = "modified_date",nullable = false)
    private Long modifiedDate;

    @LastModifiedBy
    @Column(name = "modified_by",nullable = false)
    private UUID modifiedBy;


    @Column(name = "deleted", columnDefinition = " boolean DEFAULT false")
    private boolean deleted = false;


    public FileEntity(String guid, String name, String extension, Long size, String type) {
        this.guid = guid;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.type = type;
    }

    public String getFileName() {
        return getGuid() + "." + getExtension();
    }

    public String getFileUrl() {
        return "/file/download/" + getFileName();
    }
}

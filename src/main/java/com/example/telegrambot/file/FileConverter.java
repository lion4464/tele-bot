package com.example.telegrambot.file;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileConverter {
    FileDto convertFromEntity(FileEntity fileEntity);
    List<FileDto> convertFromEntities(List<FileEntity> fileEntities);
}

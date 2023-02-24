package com.example.telegrambot.file;

import com.example.telegrambot.exceptions.DataNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public interface FileService {
    FileEntity create(MultipartFile file) throws IOException, DataNotFoundException;
    Resource loadFileAsResource(String fileName) throws DataNotFoundException;
    ResponseEntity<Resource> download(String fileName, HttpServletRequest request) throws DataNotFoundException;
    ResponseEntity<Resource> preview(String fileName, HttpServletRequest request) throws DataNotFoundException;
    void deleteFile(UUID id) throws DataNotFoundException, IOException;
    String getUploadDir();
    FileEntity createFromUrl(String url, String extension, String fileName, String contentType) throws IOException;
    FileEntity createFromBase64(String fileBase64, String extension, String fileName, String contentType) throws IOException;

    FileEntity findById(UUID id) throws DataNotFoundException;
}

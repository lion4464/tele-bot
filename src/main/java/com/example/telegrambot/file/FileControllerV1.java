package com.example.telegrambot.file;

import com.example.telegrambot.exceptions.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/api/file")
public class FileControllerV1 {
    private final FileService fileService;
    private final FileConverter fileConverter;
    private final Logger logger = LogManager.getLogger();


    public FileControllerV1(FileService fileService, FileConverter fileConverter) {
        this.fileService = fileService;
        this.fileConverter = fileConverter;
    }

    @PostMapping
        public ResponseEntity<FileDto> create(@RequestParam MultipartFile uploadedFile) throws IOException, DataNotFoundException {
        return ResponseEntity.ok(fileConverter.convertFromEntity(fileService.create(uploadedFile)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> get(@PathVariable("id") UUID id) throws DataNotFoundException{
        return ResponseEntity.ok(fileConverter.convertFromEntity(fileService.findById(id)));
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName, HttpServletRequest request) throws DataNotFoundException {
        return fileService.download(fileName, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) throws DataNotFoundException, IOException {
        fileService.deleteFile(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/preview/{fileName}")
    public ResponseEntity<Resource> preview(@PathVariable("fileName") String fileName, HttpServletRequest request) throws DataNotFoundException {
        return fileService.preview(fileName, request);
    }

}

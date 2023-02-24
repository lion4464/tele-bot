package com.example.telegrambot.file;

import com.example.telegrambot.exceptions.DataNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class FileControllerCommon {
    private final FileService fileService;
    private final FileConverter fileConverter;

    public FileControllerCommon(FileService fileService, FileConverter fileConverter) {
        this.fileService = fileService;
        this.fileConverter = fileConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> get(@PathVariable("id") UUID id) throws DataNotFoundException, FileNotFoundException {
        return ResponseEntity.ok(fileConverter.convertFromEntity(fileService.findById(id)));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName, HttpServletRequest request) throws DataNotFoundException {
        return fileService.download(fileName, request);
    }
}

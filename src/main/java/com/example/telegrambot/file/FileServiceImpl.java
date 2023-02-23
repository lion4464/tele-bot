package com.example.telegrambot.file;

import com.example.telegrambot.exceptions.DataNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${storage.upload_dir}")
    private String uploadDir;

    private final FileRepository fileRepository;

    private final Path fileStorageLocation;

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public FileEntity create(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return createFile(file.getInputStream(),extension,file.getOriginalFilename(),file.getSize(),file.getContentType());
    }

    @Override
    public Resource loadFileAsResource(String fileName) throws DataNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new DataNotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException | DataNotFoundException e) {
            throw new DataNotFoundException("File not found: " + fileName);
        }
    }

    @Override
    public ResponseEntity<Resource> download(String fileName, HttpServletRequest request) throws DataNotFoundException {
        Resource resource = loadFileAsResource(fileName);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            contentType = "application/octet-stream";;
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000")
                .body(resource);
    }

    @Override
    public ResponseEntity<Resource> preview(String fileName, HttpServletRequest request) throws DataNotFoundException {
        Resource resource = loadFileAsResource(fileName);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            contentType = "application/octet-stream";;
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000")
                .body(resource);
    }


    @Override
    public void deleteFile(UUID id) throws DataNotFoundException, IOException {
        FileEntity model = findById(id);
        Path path = Paths.get(uploadDir + File.separator + model.getFileName());
        Files.delete(path);
        fileRepository.delete(model);
    }

    public FileEntity findById(UUID id) throws FileNotFoundException {
        Optional<FileEntity> optional =  fileRepository.findById(id);
        if (Objects.isNull(optional))
            throw new FileNotFoundException("file not found");
        return optional.get();
    }

    @Override
    public String getUploadDir() {
        return uploadDir;
    }

    @Override
    public FileEntity createFromUrl(String url, String extension, String fileName, String contentType) throws IOException {
        InputStream inputStream = new URL(url).openStream();
        return createFile(inputStream,extension,fileName, (long) inputStream.available(),contentType);
    }

    @Override
    public FileEntity createFromBase64(String fileBase64, String extension, String fileName, String contentType) throws IOException {
        byte[] fileBytes = Base64.getDecoder().decode(fileBase64.getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = new ByteArrayInputStream(fileBytes);
        return createFile(inputStream,extension,fileName, (long) inputStream.available(),contentType);
    }

    private FileEntity createFile(InputStream inputStream, String extension, String fileName, Long size, String contentType) throws IOException {
        String guid = generateGuid();
        Path uploadPath = Paths.get(uploadDir + File.separator + guid + "." + extension);
        Files.copy(inputStream,uploadPath, StandardCopyOption.REPLACE_EXISTING);
        FileEntity fileEntity = new FileEntity(guid,fileName,extension,size,contentType);
        return fileRepository.save(fileEntity);
    }

    public String generateGuid() {
        return RandomString.make(20) + System.currentTimeMillis() + RandomString.make(20);
    }
}

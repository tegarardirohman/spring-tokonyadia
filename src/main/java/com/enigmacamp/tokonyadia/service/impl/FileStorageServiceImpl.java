package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.entity.User;
import com.enigmacamp.tokonyadia.service.FileStorageService;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.apache.logging.log4j.Logger;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = LogManager.getLogger(FileStorageServiceImpl.class);
    private final Path fileStorageLocation;

    public FileStorageServiceImpl() {
        this.fileStorageLocation = Path.of("assets/images/");
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, String id) {


        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        String idFileName = id + "_" + fileName;

        try {
            Path targetLocation = fileStorageLocation.resolve(idFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return idFileName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<byte[]> getImage(String img) {
        try {

            Path targetLocation = fileStorageLocation.resolve(img);

            byte[] bytes = Files.readAllBytes(targetLocation);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("image/png"));
            headers.setContentDispositionFormData("attachment", img);
            headers.setContentLength(bytes.length);

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        } catch (IOException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage() + " Not Found!");
        }

    }
}

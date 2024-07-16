package com.enigmacamp.tokonyadia.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.nio.file.FileStore;

public interface FileStorageService {
    public String storeFile(MultipartFile file, String id);

    public ResponseEntity<byte[]> getImage(String id);
}

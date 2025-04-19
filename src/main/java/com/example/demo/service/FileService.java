package com.example.demo.service;

import com.example.demo.model.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResponseEntity<?> uploadFile(MultipartFile fileToBeUploaded);
    ResponseEntity<?> downloadFile(String filename);
     String saveImage(MultipartFile file);
     byte[] afficherImage(String filename);

}

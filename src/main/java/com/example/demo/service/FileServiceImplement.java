package com.example.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class FileServiceImplement implements FileService {
    @Override
    public ResponseEntity<?> uploadFile(MultipartFile fileToBeUploaded) {
        return null;
    }

    @Override
    public ResponseEntity<?> downloadFile(String filename) {
        return null;
    }

    @Override
    public String saveImage(MultipartFile file) {
        return "";
    }

    @Override
    public byte[] afficherImage(String filename) {
        return new byte[0];
    }
}

package com.example.demo.service;

import com.example.demo.model.File;
import com.example.demo.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
public class FileServiceImplement implements FileService {
      private final FileRepository fileRepository;
    @Autowired
    public FileServiceImplement(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
    private  static final Logger LOGGER= LoggerFactory.getLogger((FileServiceImplement.class.getName()));

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile fileToBeUploaded) {
        try{
            if(!this.fileRepository.existsByFilename(fileToBeUploaded.getOriginalFilename())){
        File file = new File();
        file.setFilename(fileToBeUploaded.getOriginalFilename());
        file.setContentType(fileToBeUploaded.getContentType());
        file.setSize(fileToBeUploaded.getSize());
        file.setData(fileToBeUploaded.getBytes());
        return new ResponseEntity<>("File Uploaded Successfully "+fileRepository.save(file), HttpStatus.CREATED);}
            else {
                return new ResponseEntity<>("File already exists", HttpStatus.CONFLICT);
            }
        //return this.fileRepository.save(file);
        }
        catch(IOException e){
            LOGGER.error("error getting data from file " +e.getMessage());
            return  new ResponseEntity<>("Error when getting Data from file", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> downloadFile(String filename) {
        Optional<File> optionalFile=this.fileRepository.findFileByFilename(filename);
        if(optionalFile.isPresent()){
            File file=optionalFile.get();
            return new ResponseEntity<>(file,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
private boolean fileExists(String filename){
        return this.fileRepository.existsByFilename(filename);
}
Path imagePath= Paths.get("uploads/pdf");
    @Override
    public String saveImage(MultipartFile file) {
        String originalFilename=file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(('.')));
        String randomName= RandomStringUtils.randomAlphanumeric(10)+extension;
      try{  Files.copy(
                file.getInputStream(),
                imagePath.resolve(randomName)
        );}
      catch(IOException e){
          throw new RuntimeException(e);
      }
       return randomName;
    }

    @Override
    public byte[] afficherImage(String filename) {
        try{
        Path filePath=imagePath.resolve(filename);
        return Files.readAllBytes(filePath);}
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}

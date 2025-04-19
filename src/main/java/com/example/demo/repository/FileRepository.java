package com.example.demo.repository;

import com.example.demo.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends MongoRepository<File,String> {
    Optional<File> findFileByFilename(String filename);
    boolean existsByFilename(String filename);
}

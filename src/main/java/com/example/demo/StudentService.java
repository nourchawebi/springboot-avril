package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {
    Flux<Student> getStudents();
    Mono<Student> saveStudent(Student student);

}

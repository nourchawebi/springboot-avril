package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class StudentImplement  implements  StudentService{
    private final StudentDao studentDao;
@Autowired
    public StudentImplement(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Flux<Student> getStudents() {

       return studentDao.findAll();
    }

    @Override
    public Mono<Student> saveStudent(Student student) {
       return Mono.just(student);
    }
}

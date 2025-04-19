package com.example.demo;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public Flux<Student> getAllStudents() {
        return studentService.getStudents();
    }
    @PostMapping()
    public Mono<Student> saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }
}

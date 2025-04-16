package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(name="firstName",length = 10,nullable = true)
    @Size(max=10,message="le nom ne doit pas depasser 10 caracteres",min=3)
    private String firstName ;
    private String lastName;
    @Column(nullable = false, length = 100,unique=true)
    private String email ;
    @Column(unique=true)
    private String username;
    private String address;
    private String password;
    private String confirmPassword;


}

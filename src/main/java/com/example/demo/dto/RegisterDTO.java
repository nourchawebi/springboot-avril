package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String firstName ;
    private String lastName;

    private String email ;

    private String username;
    private String address;
    private String password;

}

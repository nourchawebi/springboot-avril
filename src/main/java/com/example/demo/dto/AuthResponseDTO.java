package com.example.demo.dto;

import com.example.demo.entities.UserEntity;
import lombok.Data;


@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private UserEntity user;

    public AuthResponseDTO(String accessToken, UserEntity user) {
        this.accessToken = accessToken;
        this.user = user;

    }


}

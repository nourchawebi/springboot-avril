package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Lob
    @Column(name = "picByte",columnDefinition = "LONGBLOB")
    byte[] picByte;

    @JsonIgnore
    @OneToOne
    @JoinTable(name="user_image_assoiation")
    private UserEntity userEntity;
}

package com.example.demo.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    public String title;
    public String content;
    @Temporal(TemporalType.DATE)
    private Date created;

}

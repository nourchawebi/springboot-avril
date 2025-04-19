package com.example.demo.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    public String title;
    public String content;
    @Temporal(TemporalType.DATE)
    private Date created;
    @ManyToOne
    private UserEntity user;
    @OneToMany(mappedBy = "post",cascade=CascadeType.ALL,fetch= FetchType.LAZY)
    private List<Comment> comments;

}

package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private Timestamp created;
    private String body;
    private String attribute4;
    @ManyToOne
    private Post post;
    @OneToMany(mappedBy = "comment",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<likes> likes;


}

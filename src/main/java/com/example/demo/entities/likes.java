package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
public class likes {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer likes;
    @ManyToOne
    @JoinColumn(name="comment_id", referencedColumnName = "commentId")
    private Comment comment;
}

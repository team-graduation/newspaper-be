package com.vn.newspaperbe.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(columnDefinition = "TEXT", nullable=false)
    private String content;

    @ManyToOne
    private News news;

    @ManyToOne
    private DAOUser user;
}


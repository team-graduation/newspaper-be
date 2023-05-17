package com.vn.newspaperbe.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
public class News
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsId;

    @Column(nullable=false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT", nullable=false)
    private String content;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String summarization;

    private String imageName;

    private Date addedDate;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    private DAOUser user;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}

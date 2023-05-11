package com.vn.newspaperbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
//    private List<News> news;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_category_id", referencedColumnName = "id")
//    private List<News> news;

    @JsonIgnore
    @OneToMany(mappedBy = "categories")
    private List<News> newsList = new ArrayList<>();

    private String name;
}
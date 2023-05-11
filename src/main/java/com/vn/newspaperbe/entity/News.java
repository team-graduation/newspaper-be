package com.vn.newspaperbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String summarization;
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private String image;
    private Date createdAt;
    private Date updatedAt;
    private boolean status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category categories;

    //    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "category_id")
//    private Category category;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private Category category;
//
//    public News() {
//    }
//
//    public News(long id, String summarization, String title, String content, String image, Date createdAt, Date updatedAt, boolean status, Category category) {
//        this.id = id;
//        this.summarization = summarization;
//        this.title = title;
//        this.content = content;
//        this.image = image;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.status = status;
//        this.category = category;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getSummarization() {
//        return summarization;
//    }
//
//    public void setSummarization(String summarization) {
//        this.summarization = summarization;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    @Override
//    public String toString() {
//        return "News{" +
//                "id=" + id +
//                ", summarization='" + summarization + '\'' +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", image='" + image + '\'' +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                ", status=" + status +
//                ", category=" + category +
//                '}';
//    }
}

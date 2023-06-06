package com.vn.newspaperbe.repository;

import com.vn.newspaperbe.entity.Comment;
import com.vn.newspaperbe.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentByNews(News news);

}

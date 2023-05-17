package com.vn.newspaperbe.repository;

import com.vn.newspaperbe.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INewsRepository extends JpaRepository<News, Integer> {
}
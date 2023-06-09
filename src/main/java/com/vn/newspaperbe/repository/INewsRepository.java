package com.vn.newspaperbe.repository;

import com.vn.newspaperbe.entity.Category;
import com.vn.newspaperbe.entity.DAOUser;
import com.vn.newspaperbe.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface INewsRepository extends JpaRepository<News, Integer> {
    List<News> findNewsByCategory(Category category);

    List<News> findNewsByUser(DAOUser user);

    List<News> findNewsByTitle(String title);

    News findByNewsId(Integer id);

    @Query(value = "select * from newspaper.news n where n.sentiment = 'POSITIVE'", nativeQuery = true)
    List<News> findNewsBySentiment();

    @Query(value = "SELECT * FROM newspaper.news n WHERE DATE(n.added_date) = :today", nativeQuery = true)
    List<News> findNewsByAddedDate(@Param("today") Date today);
}
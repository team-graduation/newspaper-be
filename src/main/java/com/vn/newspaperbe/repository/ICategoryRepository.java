package com.vn.newspaperbe.repository;

import com.vn.newspaperbe.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryId(int id);
}
package com.vn.newspaperbe.repository;

import com.vn.newspaperbe.entity.Category;
import com.vn.newspaperbe.payloads.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryId(int id);

    Category findByCategoryTitle(String title);
}
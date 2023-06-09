package com.vn.newspaperbe.service;

import com.vn.newspaperbe.entity.Category;
import com.vn.newspaperbe.payloads.CategoryDTO;
import com.vn.newspaperbe.payloads.NewsDTO;

import java.util.List;

public interface ICategoryService extends IGeneralService<Category> {
    List<CategoryDTO> getAllCategories();

}
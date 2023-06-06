package com.vn.newspaperbe.service;

import com.vn.newspaperbe.entity.Category;
import com.vn.newspaperbe.entity.News;
import com.vn.newspaperbe.payloads.NewsDTO;

import java.util.Date;
import java.util.List;

public interface INewsService extends IGeneralService<News>{
    NewsDTO createNews(NewsDTO newsDTO, Integer userId);

    NewsDTO updateNews(NewsDTO newsDTO, Integer newsId);

    List<NewsDTO> getAllNews();

    List<NewsDTO> getNewsByCategory(Category category);

    List<NewsDTO> getNewsByUsers(Integer userId);

    NewsDTO getNewsById(Integer newsId);

    List<NewsDTO> searchNews(String title);


}

package com.vn.newspaperbe.service;

import com.vn.newspaperbe.entity.News;
import com.vn.newspaperbe.payloads.NewsDTO;

import java.util.Date;
import java.util.List;

public interface INewsService extends IGeneralService<News> {
    NewsDTO createNews(NewsDTO newsDTO);

    NewsDTO updateNews(NewsDTO newsDTO, Integer newsId);

    List<NewsDTO> getAllNews();

    List<NewsDTO> getAllNewsByStatus();

    List<NewsDTO> getNewsByCategoryId(Integer categoryId);

    List<NewsDTO> getNewsByUsers();

    NewsDTO getNewsById(Integer newsId);

    List<NewsDTO> searchNews(String title);

    List<NewsDTO> getNewsBySentiment();

    List<NewsDTO> getNewsByAddedDate(Date today);

    NewsDTO acceptNews(Integer newsId);
}

package com.vn.newspaperbe.service.impl;

import com.vn.newspaperbe.entity.Category;
import com.vn.newspaperbe.entity.DAOUser;
import com.vn.newspaperbe.entity.News;
import com.vn.newspaperbe.exceptions.ResourceNotFoundException;
import com.vn.newspaperbe.payloads.NewsDTO;
import com.vn.newspaperbe.repository.ICategoryRepository;
import com.vn.newspaperbe.repository.INewsRepository;
import com.vn.newspaperbe.repository.IUserRepository;
import com.vn.newspaperbe.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService implements INewsService {

    @Autowired
    private INewsRepository iNewsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Override
    public Iterable<News> findAll() {
        return null;
    }

    @Override
    public Optional<News> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public News save(News news) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        News news = this.iNewsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("News", "Id", id));
        iNewsRepository.delete(news);
    }

    @Override
    public NewsDTO createNews(NewsDTO newsDTO, Integer userId) {
        DAOUser user = this.iUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        News news = this.modelMapper.map(newsDTO, News.class);
        news.setImageName("default.png");
        news.setAddedDate(new Date());
        news.setUser(user);

        News savedNews = this.iNewsRepository.save(news);

        return this.modelMapper.map(news, NewsDTO.class);
    }

    @Override
    public NewsDTO updateNews(NewsDTO newsDTO, Integer newsId) {
        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setImageName(newsDTO.getImageName());

        News updatedPost = iNewsRepository.save(news);

        return this.modelMapper.map(updatedPost, NewsDTO.class);
    }

    @Override
    public List<NewsDTO> getAllNews() {
        List<News> news = this.iNewsRepository.findAll();
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());
        return newsDTOS;
    }

    @Override
    public NewsDTO getNewsById(Integer newsId) {
        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
        return this.modelMapper.map(newsId, NewsDTO.class);
    }
}

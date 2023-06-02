package com.vn.newspaperbe.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

    static int classifyValue = 0;
    static String summaryValue = "";
    static String sentimentValue = "";

    @Override
    public NewsDTO createNews(NewsDTO newsDTO, Integer userId) {
        DAOUser user = this.iUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        News news = this.modelMapper.map(newsDTO, News.class);
        news.setImageName("default.png");
        news.setAddedDate(new Date());
        news.setUser(user);

        try {
            sendText(news.getContent());
            news.setCategory(iCategoryRepository.findByCategoryId(classifyValue));
            news.setSummarization(summaryValue);
            news.setSentiment(sentimentValue);

        } catch (Exception e) {
            System.out.println(e);
        }


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
    public List<NewsDTO> getNewsByCategory(Integer categoryId) {
        List<News> news = this.iNewsRepository.findNewsByCategory(
                iCategoryRepository.findByCategoryId(categoryId)
        );
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());
        return newsDTOS;
    }

    @Override
    public List<NewsDTO> getNewsByUsers(Integer userId) {
        DAOUser user = this.iUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        List<News> news = this.iNewsRepository.findNewsByUser(user);
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());

        return newsDTOS;
    }

    @Override
    public NewsDTO getNewsById(Integer newsId) {
        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
        return this.modelMapper.map(news, NewsDTO.class);
    }

    public static void sendText(String t) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:5555/api/receive-text";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = t;
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl,
                HttpMethod.POST,
                requestEntity, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String jsonString = responseEntity.getBody();
            System.out.println("Gửi văn bản thành công!");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            classifyValue = jsonNode.get("classify").asInt();
            summaryValue = jsonNode.get("summary").asText();
            sentimentValue = jsonNode.get("sentiment").asText();
            System.out.println(classifyValue);
            System.out.println(summaryValue);
        } else {
            System.out.println("Gửi văn bản thất bại. Mã phản hồi: "
                    + responseEntity.getStatusCodeValue());
        }
    }

}

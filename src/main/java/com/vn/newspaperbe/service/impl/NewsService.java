package com.vn.newspaperbe.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.newspaperbe.entity.DAOUser;
import com.vn.newspaperbe.entity.News;
import com.vn.newspaperbe.exceptions.ResourceNotFoundException;
import com.vn.newspaperbe.payloads.NewsDTO;
import com.vn.newspaperbe.payloads.NewsResponse;
import com.vn.newspaperbe.repository.ICategoryRepository;
import com.vn.newspaperbe.repository.INewsRepository;
import com.vn.newspaperbe.repository.IUserRepository;
import com.vn.newspaperbe.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
    public List<News> findAll() {
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

//    @Override
//    public NewsDTO createNews(NewsDTO newsDTO, Integer userId) {
//        DAOUser user = this.iUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
//
//        News news = this.modelMapper.map(newsDTO, News.class);
//        news.setImage("default.png");
//        news.setAddedDate(new Date());
//        news.setUser(user);
//
//        try {
//            sendText(news.getContent());
//            news.setCategory(iCategoryRepository.findByCategoryId(classifyValue));
//            news.setSummarization(summaryValue);
//            news.setSentiment(sentimentValue);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        News savedNews = this.iNewsRepository.save(news);
//        return this.modelMapper.map(news, NewsDTO.class);
//    }

    @Override
    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = this.modelMapper.map(newsDTO, News.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            DAOUser user = iUserRepository.findByUsername(username);
            System.out.println(user);
            news.setUser(user);
        }
        news.setAddedDate(new Date());
        news.setStatus(false);

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
//        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
//        System.out.println(newsDTO);
//        news.setTitle(newsDTO.getTitle());
//
//        news.setContent(newsDTO.getContent());
//        news.setThumbnail(newsDTO.getThumbnail());
//
//        News updatedPost = iNewsRepository.save(news);
//
//        return this.modelMapper.map(updatedPost, NewsDTO.class);
        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
        System.out.println(newsDTO.getTitle()+"abcd");
        news.setTitle(newsDTO.getTitle());
        news.setThumbnail(newsDTO.getThumbnail());
        System.out.println(newsDTO.getThumbnail());
//        System.out.println(newsDTO.getContent());
//        news.setContent(newsDTO.getContent());
        if(!(news.getContent().equalsIgnoreCase(newsDTO.getContent()))){
            System.out.println("abcdef");
            System.out.println(newsDTO.getContent());
            news.setContent(newsDTO.getContent());
            try {
                sendText(news.getContent());
                news.setCategory(iCategoryRepository.findByCategoryId(classifyValue));
                news.setSummarization(summaryValue);
                news.setSentiment(sentimentValue);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
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
    public List<NewsDTO> getAllNewsByStatus() {
        List<News> news = this.iNewsRepository.findAllByStatus(true);
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());
        return newsDTOS;
    }


    @Override
    public List<NewsDTO> getNewsByCategoryId(Integer categoryId) {
        List<News> news = this.iNewsRepository.findNewsByCategory(
                this.iCategoryRepository.findByCategoryId(categoryId)
        );
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());
        return newsDTOS;
    }

    @Override
    public List<NewsDTO> getNewsByUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<News> news = this.iNewsRepository.findNewsByUser(
                iUserRepository.findByUsername(currentPrincipalName)
        );
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());
        return newsDTOS;
    }

    @Override
    public NewsDTO getNewsById(Integer newsId) {
        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
        return this.modelMapper.map(news, NewsDTO.class);
    }

    @Override
    public List<NewsDTO> searchNews(String title) {
        List<News> news = this.iNewsRepository.findNewsByTitle(title);
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());
        return newsDTOS;
    }

    @Override
    public List<NewsDTO> getNewsBySentiment() {
        List<News> news = this.iNewsRepository.findNewsBySentiment();
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());
        return newsDTOS;
    }

    @Override
    public List<NewsDTO> getNewsByAddedDate(Date today) {
        List<News> news = this.iNewsRepository.findNewsByAddedDate(today);
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());
        return newsDTOS;
    }

    @Override
    public NewsDTO acceptNews(Integer newsId) {
        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
        news.setStatus(true);
        News updatedStatus = iNewsRepository.save(news);
        return this.modelMapper.map(updatedStatus, NewsDTO.class);
    }

    // Pagination
    @Override
    public NewsResponse getAllNewsByPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pg = PageRequest.of(pageNumber, pageSize, sort);

        Page<News> pageNews = this.iNewsRepository.findAll(pg);
        List<News> news = pageNews.getContent();

        //content
        List<NewsDTO> newsDTOS = news.stream().map((p) -> this.modelMapper.map(p, NewsDTO.class)).collect(Collectors.toList());

        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setContent(newsDTOS);
        newsResponse.setPageNumber(pageNews.getNumber());
        newsResponse.setPageSize(pageNews.getSize());
        newsResponse.setTotalElements(pageNews.getTotalElements());
        newsResponse.setTotalPages(pageNews.getTotalPages());
        newsResponse.setLastPage(pageNews.isLast());

        return newsResponse;
    }

    public static void sendText(String t) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://157.245.192.252/api/receive-text";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        String requestBody = t;
        String requestBody = extractAllText(t);
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

    public static String extractAllText(String html) {
        Document doc = Jsoup.parse(html);

        return getAllText(doc);
    }

    public static String getAllText(Element element) {
        StringBuilder sb = new StringBuilder();

        for (Element child : element.children()) {
            sb.append(getAllText(child)).append(" ");
        }

        String ownText = element.ownText().trim();
        if (!ownText.isEmpty()) {
            sb.append(ownText).append(" ");
        }

        return sb.toString().trim();
    }

}

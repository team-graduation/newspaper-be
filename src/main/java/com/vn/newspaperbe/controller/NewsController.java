package com.vn.newspaperbe.controller;

import com.vn.newspaperbe.payloads.ApiResponse;
import com.vn.newspaperbe.payloads.NewsDTO;
import com.vn.newspaperbe.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class NewsController {

    @Autowired
    private INewsService iNewsService;

    //Create news
    @PostMapping("/user/{userId}/news")
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO newsDTO, @PathVariable Integer userId)
    {
        NewsDTO savedNews = this.iNewsService.createNews(newsDTO, userId);
        return new ResponseEntity<NewsDTO>(savedNews, HttpStatus.CREATED);
    }

    //Update news
    @PutMapping("/news/{newsId}")
    public ResponseEntity<NewsDTO> updateNews(@RequestBody NewsDTO newsDTO,@PathVariable Integer newsId)
    {
        NewsDTO news = iNewsService.updateNews(newsDTO, newsId);
        return new ResponseEntity<NewsDTO>(news,HttpStatus.OK);
    }

    //Get all news
    @GetMapping("/news")
    public ResponseEntity<List<NewsDTO>> getAllNews(){
        List<NewsDTO> news = this.iNewsService.getAllNews();
        return new ResponseEntity<List<NewsDTO>>(news,HttpStatus.OK);
    }

    //Get news by ID
    @GetMapping("/news/{newsId}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Integer newsId)
    {
        NewsDTO news = this.iNewsService.getNewsById(newsId);
        return new ResponseEntity<NewsDTO>(news,HttpStatus.OK);
    }

    //Delete news
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<ApiResponse> deleteNews(@PathVariable Integer newsId)
    {
        iNewsService.delete(newsId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("News deleted successfully",true),HttpStatus.OK);
    }

}

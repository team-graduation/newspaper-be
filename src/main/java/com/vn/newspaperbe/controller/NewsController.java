package com.vn.newspaperbe.controller;

import com.vn.newspaperbe.entity.Category;
import com.vn.newspaperbe.payloads.ApiResponse;
import com.vn.newspaperbe.payloads.NewsDTO;
import com.vn.newspaperbe.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
public class NewsController {

    @Autowired
    private INewsService iNewsService;

    //Create news
//    @PostMapping("/user/{userId}/dh-news")
//    public ResponseEntity<NewsDTO> createDhNews(@RequestParam("image_thumbnail") MultipartFile image_thumbnail,
//                                                @RequestParam("title") String title,
//                                                @RequestParam("content") String content,
//                                                @PathVariable Integer userId) {
//        String thumbnail = saveImage(image_thumbnail);
//        NewsDTO newsDTO = new NewsDTO();
//        newsDTO.setTitle(title);
//        newsDTO.setThumbnail(thumbnail);
//        newsDTO.setContent(content);
//        NewsDTO savedNews = this.iNewsService.createNews(newsDTO, userId);
//        return new ResponseEntity<NewsDTO>(savedNews, HttpStatus.CREATED);
//    }

    @PostMapping("/news/add")
    public ResponseEntity<NewsDTO> createNewsTest(@RequestParam("thumbnail") MultipartFile thumbnail,
                                                @RequestParam("title") String title,
                                                @RequestParam("content") String content) {
        String thumbnailImage = saveImage(thumbnail);
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(title);
        newsDTO.setThumbnail(thumbnailImage);
        newsDTO.setContent(content);
        NewsDTO savedNews = this.iNewsService.createNews(newsDTO);
        return new ResponseEntity<NewsDTO>(savedNews, HttpStatus.CREATED);
    }


    //Update news
    @PutMapping("/news/{newsId}")
    public ResponseEntity<NewsDTO> updateNews(@RequestParam("thumbnail") MultipartFile thumbnail,
                                              @RequestParam("title") String title,
                                              @RequestParam("content") String content, @PathVariable Integer newsId) {
        String thumbnailImage = saveImage(thumbnail);
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(title);
        newsDTO.setThumbnail(thumbnailImage);
        newsDTO.setContent(content);
        NewsDTO news = iNewsService.updateNews(newsDTO, newsId);
        return new ResponseEntity<NewsDTO>(news, HttpStatus.OK);
    }

    //Get all news
    @GetMapping("/news")
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<NewsDTO> news = this.iNewsService.getAllNews();
        return new ResponseEntity<List<NewsDTO>>(news, HttpStatus.OK);
    }

    @GetMapping("/news/recommend")
    public ResponseEntity<List<NewsDTO>> getNewsBySentiment() {
        List<NewsDTO> news = this.iNewsService.getNewsBySentiment();
        return new ResponseEntity<List<NewsDTO>>(news, HttpStatus.OK);
    }

    //Get news by ID
    @GetMapping("/news/{newsId}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Integer newsId) {
        NewsDTO news = this.iNewsService.getNewsById(newsId);
        return new ResponseEntity<NewsDTO>(news, HttpStatus.OK);
    }

    //Get news by category
    @GetMapping("/news/category/{categoryId}")
    public ResponseEntity<List<NewsDTO>> getNewsByCategory(@PathVariable Integer categoryId)
    {
        List<NewsDTO> news = this.iNewsService.getNewsByCategoryId(categoryId);
        return new ResponseEntity<List<NewsDTO>>(news,HttpStatus.OK);
    }

    //Get news today
    @GetMapping("/news/today")
    public ResponseEntity<List<NewsDTO>> getNewsByToday()
    {
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        List<NewsDTO> news = this.iNewsService.getNewsByAddedDate(date);
        return new ResponseEntity<List<NewsDTO>>(news,HttpStatus.OK);
    }

    //Get news by user
    @GetMapping("/user/{userId}/news")
    public ResponseEntity<List<NewsDTO>> getNewsByUser(@PathVariable Integer userId) {
        List<NewsDTO> news = this.iNewsService.getNewsByUsers(userId);
        return new ResponseEntity<List<NewsDTO>>(news, HttpStatus.OK);
    }

//    @GetMapping("/addDate}")
//    public ResponseEntity<List<NewsDTO>> findNewsByAddedDate(){
//        List<NewsDTO> news = this.iNewsService.findNewsByAddedDate();
//        return new ResponseEntity<List<NewsDTO>>(news,HttpStatus.OK);
//    }

    //Delete news
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<ApiResponse> deleteNews(@PathVariable Integer newsId) {
        iNewsService.delete(newsId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("News deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/news/search/{title}")
    public ResponseEntity<List<NewsDTO>> searchNewsByTitle(@PathVariable("title") String title) {
        List<NewsDTO> result = this.iNewsService.searchNews(title);
        return new ResponseEntity<List<NewsDTO>>(result, HttpStatus.OK);
    }


    public String saveImage(MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                // Xác định đường dẫn thư mục lưu trữ ảnh
                String projectPath = System.getProperty("user.dir");
                String directoryPath = projectPath + "/images/";

                // Tạo thư mục nếu chưa tồn tại
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Lưu file vào thư mục trong project
                String fileName = System.currentTimeMillis() + "-" + image.getOriginalFilename();
                String filePath = directoryPath + fileName;
                image.transferTo(new File(filePath));

                // Tạo đường dẫn đầy đủ của ảnh
                String imageUrl = "http://localhost:8080/api/auth/images/" + fileName;

                // Trả về đường dẫn của ảnh
                return imageUrl;
            } catch (IOException e) {
                // Xử lý lỗi nếu có
                e.printStackTrace();

            }
        }
        return null;
    }

    @GetMapping("/images/{image}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("image") String image) {
        if (!image.equals("") || image != null) {
            try {
                Path filename = Paths.get("images", image);
                byte[] buffer = Files.readAllBytes(filename);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok().contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/jpg"))
                        .body(byteArrayResource);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return ResponseEntity.badRequest().build();
    }

}

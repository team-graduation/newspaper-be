package com.vn.newspaperbe.controller;

import com.vn.newspaperbe.payloads.ApiResponse;
import com.vn.newspaperbe.payloads.CommentDTO;
import com.vn.newspaperbe.payloads.NewsDTO;
import com.vn.newspaperbe.service.ICommentService;
import com.vn.newspaperbe.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/")
public class CommentController {
    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private INewsService iNewsService;

    @GetMapping("/news/{newsId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentByNews(@PathVariable Integer newsId)
    {
        List<CommentDTO> comments = this.iCommentService.getCommentByNewsId(newsId);
        return new ResponseEntity<List<CommentDTO>>(comments,HttpStatus.OK);
    }

    @PostMapping("/news/{newsId}/user/{userId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment, @PathVariable Integer newsId, @PathVariable Integer userId)
    {
        CommentDTO createComment = this.iCommentService.createComment(comment, newsId, userId);
        return new ResponseEntity<CommentDTO>(createComment, HttpStatus.CREATED);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer commentId)
    {
        CommentDTO comment = iCommentService.updateComment(commentDTO, commentId);
        return new ResponseEntity<CommentDTO>(comment,HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
    {
        this.iCommentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
    }
}

package com.vn.newspaperbe.service.impl;

import com.vn.newspaperbe.entity.Comment;
import com.vn.newspaperbe.entity.DAOUser;
import com.vn.newspaperbe.entity.News;
import com.vn.newspaperbe.exceptions.ResourceNotFoundException;
import com.vn.newspaperbe.payloads.CommentDTO;
import com.vn.newspaperbe.payloads.NewsDTO;
import com.vn.newspaperbe.repository.ICommentRepository;
import com.vn.newspaperbe.repository.INewsRepository;
import com.vn.newspaperbe.repository.IUserRepository;
import com.vn.newspaperbe.service.ICommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {
    private final IUserRepository iUserRepository;

    @Autowired
    private INewsRepository iNewsRepository;

    @Autowired
    private ICommentRepository iCommentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CommentService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer newsId, Integer userId) {
        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
        DAOUser user = this.iUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setNews(news);
        comment.setUser(user);
        Comment savedComment = iCommentRepository.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getCommentByNewsId(Integer newsId) {
        List<Comment> comments = this.iCommentRepository.findCommentByNews(
                this.iNewsRepository.findByNewsId(newsId)
        );
        List<CommentDTO> commentDTOS = comments.stream().map((p) -> this.modelMapper.map(p, CommentDTO.class)).collect(Collectors.toList());
        return commentDTOS;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.iCommentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        this.iCommentRepository.delete(comment);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Integer commentId) {
        Comment comment = this.iCommentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", commentId));
        comment.setContent(commentDTO.getContent());

        Comment updatedComment = iCommentRepository.save(comment);
        return this.modelMapper.map(updatedComment, CommentDTO.class);
    }
}

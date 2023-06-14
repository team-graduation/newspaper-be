package com.vn.newspaperbe.service;

import com.vn.newspaperbe.entity.News;
import com.vn.newspaperbe.payloads.CommentDTO;

import java.util.List;

public interface ICommentService {
    CommentDTO createComment(CommentDTO commentDTO, Integer newsId);

    List<CommentDTO> getCommentByNewsId(Integer newsId);

    void deleteComment(Integer commentId);

    CommentDTO updateComment(CommentDTO commentDTO, Integer commentId);
}

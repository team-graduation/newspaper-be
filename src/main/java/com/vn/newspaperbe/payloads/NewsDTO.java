package com.vn.newspaperbe.payloads;

import com.vn.newspaperbe.entity.Category;
import com.vn.newspaperbe.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class NewsDTO {
    private Integer newsId;

    private String title;

    private String language;

    private String content;

    private String summarization;

    private String imageName;

    private Date addedDate;

    private boolean status;

    private CategoryDTO category;

    private UserDTO user;

    private List<Comment> comments = new ArrayList<>();
}

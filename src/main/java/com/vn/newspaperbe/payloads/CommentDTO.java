package com.vn.newspaperbe.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private Integer id;

    private String content;

    private UserDTO user;


}

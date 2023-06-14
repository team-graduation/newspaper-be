package com.vn.newspaperbe.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer id;

    private String username;

    private String password;

    private String experience;

    private String phone;

    private String website;

    private String email;

    private String about;
}

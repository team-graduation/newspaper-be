package com.vn.newspaperbe.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Integer categoryId;
    @NotBlank
    @Size(min = 3,message = "Length of title should be minimum 3")
    private String categoryTitle;
}

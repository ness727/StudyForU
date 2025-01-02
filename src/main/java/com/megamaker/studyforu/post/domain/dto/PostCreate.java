package com.megamaker.studyforu.post.domain.dto;

import com.megamaker.studyforu.post.domain.vo.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreate {
    private Integer categoryId;

    private String title;

    private String body;

    private Integer price;

    private Status status;
}

package com.megamaker.studyforu.post.domain;

import com.megamaker.studyforu.post.domain.vo.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Post {
    private Long id;

    private Long userId;

    private Integer categoryId;

    private String title;

    private String body;

    private Integer price;

    private Integer likes;

    private Status status;
}

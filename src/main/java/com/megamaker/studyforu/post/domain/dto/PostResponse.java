package com.megamaker.studyforu.post.domain.dto;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.vo.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
    private final Long id;

    private final Integer categoryId;

    private final String title;

    private final String body;

    private final Integer price;

    private final Integer likes;

    private final Status status;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .categoryId(post.getCategoryId())
                .title(post.getTitle())
                .body(post.getBody())
                .price(post.getPrice())
                .likes(post.getLikes())
                .status(post.getStatus())
                .build();
    }
}

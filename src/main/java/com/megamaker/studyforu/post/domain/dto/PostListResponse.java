package com.megamaker.studyforu.post.domain.dto;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.vo.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponse {
    private final Long id;

    private final String title;

    private final String writerName;

    private final Integer price;

    private final Integer likes;

    private final Status status;

    public static PostListResponse from(Post post) {
        return PostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .writerName(post.getUserInfo().getName())
                .price(post.getPrice())
                .likes(post.getLikes())
                .status(post.getStatus())
                .build();
    }
}

package com.megamaker.studyforu.post.domain;

import com.megamaker.studyforu.post.domain.dto.PostCreate;
import com.megamaker.studyforu.post.domain.dto.PostUpdate;
import com.megamaker.studyforu.post.domain.vo.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Post {
    private final Long id;

    private final Long userId;

    private final Integer categoryId;

    private final String title;

    private final String body;

    private final Integer price;

    private final Integer likes;

    private final Status status;

    public static Post create(PostCreate postCreate, Long userId) {
        return Post.builder()
                .userId(userId)
                .categoryId(postCreate.getCategoryId())
                .title(postCreate.getTitle())
                .body(postCreate.getBody())
                .price(postCreate.getPrice())
                .likes(0)
                .status(postCreate.getStatus())
                .build();
    }

    public Post update(PostUpdate postUpdate) {
        return Post.builder()
                .id(id)
                .userId(userId)
                .categoryId(postUpdate.getCategoryId())
                .title(postUpdate.getTitle())
                .body(postUpdate.getBody())
                .price(postUpdate.getPrice())
                .likes(likes)
                .status(postUpdate.getStatus())
                .build();
    }
}

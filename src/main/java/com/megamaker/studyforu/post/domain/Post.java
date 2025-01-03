package com.megamaker.studyforu.post.domain;

import com.megamaker.studyforu.post.domain.dto.PostCreate;
import com.megamaker.studyforu.post.domain.dto.PostUpdate;
import com.megamaker.studyforu.post.domain.vo.Status;
import com.megamaker.studyforu.post.domain.vo.UserInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Post {
    private final Long id;

    private final UserInfo userInfo;

    private final Integer categoryId;

    private final String title;

    private final String body;

    private final Integer price;

    private final Integer likes;

    private final Status status;

    public static Post create(PostCreate postCreate, UserInfo userInfo) {
        return Post.builder()
                .userInfo(userInfo)
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
                .categoryId(postUpdate.getCategoryId())
                .title(postUpdate.getTitle())
                .userInfo(userInfo)
                .body(postUpdate.getBody())
                .price(postUpdate.getPrice())
                .likes(likes)
                .status(postUpdate.getStatus())
                .build();
    }
}

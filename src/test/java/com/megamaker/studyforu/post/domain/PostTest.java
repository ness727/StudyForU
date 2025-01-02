package com.megamaker.studyforu.post.domain;

import com.megamaker.studyforu.post.domain.dto.PostCreate;
import com.megamaker.studyforu.post.domain.dto.PostUpdate;
import com.megamaker.studyforu.post.domain.vo.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {
    @DisplayName("PostCreate 객체를 Post로 변환 성공한다")
    @Test
    void createSuccess() {
        // given
        PostCreate postCreate = new PostCreate(3, "title", "body", 260, Status.SECRET);
        Long userId = 2L;

        // when
        Post createdPost = Post.create(postCreate, userId);

        // then
        assertThat(createdPost.getId()).isNull();
        assertThat(createdPost.getCategoryId()).isEqualTo(postCreate.getCategoryId());
        assertThat(createdPost.getTitle()).isEqualTo(postCreate.getTitle());
        assertThat(createdPost.getBody()).isEqualTo(postCreate.getBody());
        assertThat(createdPost.getPrice()).isEqualTo(postCreate.getPrice());
        assertThat(createdPost.getStatus()).isEqualTo(postCreate.getStatus());
        assertThat(createdPost.getUserId()).isEqualTo(userId);
        assertThat(createdPost.getLikes()).isEqualTo(0);
    }

    @DisplayName("Post 객체 수정에 성공한다")
    @Test
    void updateSuccess() {
        // given
        Long userId = 6346L;
        Integer likes = 264;
        Post post = Post.builder()
                .id(34L)
                .userId(userId)
                .categoryId(745)
                .title("title")
                .body("body")
                .price(350)
                .likes(likes)
                .status(Status.PRIVATE)
                .build();
        PostUpdate postUpdate = new PostUpdate(2L ,3, "title", "body", 260, Status.SECRET);

        // when
        Post updatedPost = post.update(postUpdate);

        // then
        assertThat(updatedPost.getId()).isEqualTo(post.getId());
        assertThat(updatedPost.getUserId()).isEqualTo(userId);
        assertThat(updatedPost.getLikes()).isEqualTo(likes);

        assertThat(updatedPost.getCategoryId()).isEqualTo(postUpdate.getCategoryId());
        assertThat(updatedPost.getTitle()).isEqualTo(postUpdate.getTitle());
        assertThat(updatedPost.getBody()).isEqualTo(postUpdate.getBody());
        assertThat(updatedPost.getPrice()).isEqualTo(postUpdate.getPrice());
        assertThat(updatedPost.getStatus()).isEqualTo(postUpdate.getStatus());
    }
}
package com.megamaker.studyforu.post.domain.dto;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.vo.Status;
import com.megamaker.studyforu.post.domain.vo.UserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PostResponseTest {

    @DisplayName("Post 객체를 PostResponse 객체로 변환 성공한다")
    @Test
    void fromSuccess() {
        // given
        Post post = Post.builder()
                .id(345L)
                .userInfo(new UserInfo(3L, "kim"))
                .categoryId(63)
                .title("title")
                .body("body")
                .price(500)
                .likes(67)
                .status(Status.PUBLIC)
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getId()).isEqualTo(post.getId());
        assertThat(postResponse.getCategoryId()).isEqualTo(post.getCategoryId());
        assertThat(postResponse.getTitle()).isEqualTo(post.getTitle());
        assertThat(postResponse.getWriterName()).isEqualTo(post.getUserInfo().getName());
        assertThat(postResponse.getBody()).isEqualTo(post.getBody());
        assertThat(postResponse.getLikes()).isEqualTo(post.getLikes());
        assertThat(postResponse.getPrice()).isEqualTo(post.getPrice());
        assertThat(postResponse.getStatus()).isEqualTo(post.getStatus());
    }
}
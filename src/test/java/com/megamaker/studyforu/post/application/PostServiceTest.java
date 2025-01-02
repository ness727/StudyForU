package com.megamaker.studyforu.post.application;

import com.megamaker.studyforu.mock.FakePostBlockRepository;
import com.megamaker.studyforu.mock.FakePostRepository;
import com.megamaker.studyforu.post.domain.dto.PostCreate;
import com.megamaker.studyforu.post.domain.dto.PostCreateRequest;
import com.megamaker.studyforu.post.domain.dto.PostPostBlockResponse;
import com.megamaker.studyforu.post.domain.dto.PostResponse;
import com.megamaker.studyforu.post.domain.vo.Status;
import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockCreate;
import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockResponse;
import com.megamaker.studyforu.post.postblock.domain.vo.BlockType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {
    private final PostService postService;

    public PostServiceTest() {
        postService = new PostService(new FakePostRepository(), new FakePostBlockRepository());
    }

    @DisplayName("게시글 저장에 성공한다")
    @Test
    void saveSuccess() {
        // given
        PostCreate postCreate = new PostCreate(2, "title", "body", 300, Status.PUBLIC);
        List<PostBlockCreate> postBlockCreateList = List.of(
                new PostBlockCreate(0, BlockType.IMAGE, "block body0"),
                new PostBlockCreate(1, BlockType.TEXT, "block body1"),
                new PostBlockCreate(2, BlockType.LINK, "block body2")
        );
        PostCreateRequest postCreateRequest = new PostCreateRequest(postCreate, postBlockCreateList);
        Long userId = 2L;

        // when
        Long postId = postService.save(postCreateRequest, userId);
        PostPostBlockResponse postPostBlockResponse = postService.findByIdWithPostBlock(postId);
        PostResponse postResponse = postPostBlockResponse.getPost();
        List<PostBlockResponse> postBlockResponseList = postPostBlockResponse.getPostBlock();

        // then
        /*
            PostResponse 검증
         */
        assertThat(postResponse.getId()).isEqualTo(postId);
        assertThat(postResponse.getCategoryId()).isEqualTo(postCreate.getCategoryId());
        assertThat(postResponse.getTitle()).isEqualTo(postCreate.getTitle());
        assertThat(postResponse.getBody()).isEqualTo(postCreate.getBody());
        assertThat(postResponse.getPrice()).isEqualTo(postCreate.getPrice());
        assertThat(postResponse.getLikes()).isEqualTo(0);
        assertThat(postResponse.getStatus()).isEqualTo(postCreate.getStatus());

        /*
            PostBlockResponse 검증
         */
        assertThat(postBlockResponseList.size()).isEqualTo(3);
        assertThat(postBlockResponseList.get(0).getIndex()).isEqualTo(postBlockCreateList.get(0).getIndex());
        assertThat(postBlockResponseList.get(0).getBlockType()).isEqualTo(postBlockCreateList.get(0).getBlockType());
        assertThat(postBlockResponseList.get(0).getBody()).isEqualTo(postBlockCreateList.get(0).getBody());

        assertThat(postBlockResponseList.get(1).getIndex()).isEqualTo(postBlockCreateList.get(1).getIndex());
        assertThat(postBlockResponseList.get(1).getBlockType()).isEqualTo(postBlockCreateList.get(1).getBlockType());
        assertThat(postBlockResponseList.get(1).getBody()).isEqualTo(postBlockCreateList.get(1).getBody());

        assertThat(postBlockResponseList.get(2).getIndex()).isEqualTo(postBlockCreateList.get(2).getIndex());
        assertThat(postBlockResponseList.get(2).getBlockType()).isEqualTo(postBlockCreateList.get(2).getBlockType());
        assertThat(postBlockResponseList.get(2).getBody()).isEqualTo(postBlockCreateList.get(2).getBody());
    }
}
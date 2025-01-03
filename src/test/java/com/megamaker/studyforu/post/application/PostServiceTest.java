package com.megamaker.studyforu.post.application;

import com.megamaker.studyforu.mock.FakePostBlockRepository;
import com.megamaker.studyforu.mock.FakePostRepository;
import com.megamaker.studyforu.post.domain.PostRepository;
import com.megamaker.studyforu.post.domain.dto.*;
import com.megamaker.studyforu.post.domain.vo.Status;
import com.megamaker.studyforu.post.domain.vo.UserInfo;
import com.megamaker.studyforu.post.postblock.domain.PostBlockRepository;
import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockCreate;
import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockResponse;
import com.megamaker.studyforu.post.postblock.domain.vo.BlockType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {
    PostService postService;

    @BeforeEach
    void init() {
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
        UserInfo userInfo = new UserInfo(2L, "good");

        // when
        Long postId = postService.save(postCreateRequest, userInfo);
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

    @DisplayName("게시글 수정에 성공한다")
    @Test
    void updateSuccess() {
        // given
        PostCreate postCreate = new PostCreate(1, "title1", "body1", 100, Status.PUBLIC);
        List<PostBlockCreate> postBlockCreateList = List.of(
                new PostBlockCreate(0, BlockType.IMAGE, "block body1"),
                new PostBlockCreate(1, BlockType.TEXT, "block body2"),
                new PostBlockCreate(2, BlockType.LINK, "block body3")
        );
        PostCreateRequest postCreateRequest = new PostCreateRequest(postCreate, postBlockCreateList);
        UserInfo userInfo = new UserInfo(2L, "good");

        // when
        Long postId = postService.save(postCreateRequest, userInfo);

        /*
            수정
         */
        PostUpdate postUpdate = new PostUpdate(postId, 2, "title2", "body2", 200, Status.PRIVATE);
        List<PostBlockCreate> postBlockUpdateList = List.of(
                new PostBlockCreate(0, BlockType.IMAGE, "block after1"),
                new PostBlockCreate(1, BlockType.IMAGE, "block after2"),
                new PostBlockCreate(2, BlockType.IMAGE, "block after3")
        );
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(postUpdate, postBlockUpdateList);
        postService.update(postUpdateRequest);

        /*
            수정된 내역 조회
         */
        PostPostBlockResponse postPostBlockResponse = postService.findByIdWithPostBlock(postId);
        PostResponse postResponse = postPostBlockResponse.getPost();
        List<PostBlockResponse> postBlockResponseList = postPostBlockResponse.getPostBlock();
        PostBlockResponse postBlock0 = postBlockResponseList.get(0);
        PostBlockResponse postBlock1 = postBlockResponseList.get(1);
        PostBlockResponse postBlock2 = postBlockResponseList.get(2);

        // then
        assertThat(postResponse.getId()).isEqualTo(postUpdate.getId());
        assertThat(postResponse.getCategoryId()).isEqualTo(postUpdate.getCategoryId());
        assertThat(postResponse.getTitle()).isEqualTo(postUpdate.getTitle());
        assertThat(postResponse.getBody()).isEqualTo(postUpdate.getBody());
        assertThat(postResponse.getPrice()).isEqualTo(postUpdate.getPrice());
        assertThat(postResponse.getStatus()).isEqualTo(postUpdate.getStatus());
        assertThat(postResponse.getLikes()).isEqualTo(0);

        assertThat(postBlock0.getIndex()).isEqualTo(postBlockUpdateList.get(0).getIndex());
        assertThat(postBlock0.getBody()).isEqualTo(postBlockUpdateList.get(0).getBody());
        assertThat(postBlock0.getBlockType()).isEqualTo(postBlockUpdateList.get(0).getBlockType());

        assertThat(postBlock1.getIndex()).isEqualTo(postBlockUpdateList.get(1).getIndex());
        assertThat(postBlock1.getBody()).isEqualTo(postBlockUpdateList.get(1).getBody());
        assertThat(postBlock1.getBlockType()).isEqualTo(postBlockUpdateList.get(1).getBlockType());

        assertThat(postBlock2.getIndex()).isEqualTo(postBlockUpdateList.get(2).getIndex());
        assertThat(postBlock2.getBody()).isEqualTo(postBlockUpdateList.get(2).getBody());
        assertThat(postBlock2.getBlockType()).isEqualTo(postBlockUpdateList.get(2).getBlockType());
    }
}
package com.megamaker.studyforu.post.application;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.PostRepository;
import com.megamaker.studyforu.post.domain.dto.*;
import com.megamaker.studyforu.post.domain.vo.UserInfo;
import com.megamaker.studyforu.post.exception.PostNotFoundException;
import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.PostBlockRepository;
import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockCreate;
import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockResponse;
import com.megamaker.studyforu.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostBlockRepository postBlockRepository;

    public Long save(PostCreateRequest postCreateRequest, UserInfo userInfo) {
        // Post 저장
        PostCreate postCreate = postCreateRequest.getPostCreate();
        Long postId = postRepository.save(Post.create(postCreate, userInfo));
        
        // PostBlock 리스트 저장
        List<PostBlockCreate> postBlockCreateList = postCreateRequest.getPostBlockCreateList();
        postBlockRepository.saveAll(
                postBlockCreateList.stream()
                        .map(postBlockCreate -> PostBlock.create(postBlockCreate, postId))
                        .toList()
        );

        return postId;
    }

    public void update(PostUpdateRequest postUpdateRequest) {
        // Post 수정
        Long postId = postUpdateRequest.getPostUpdate().getId();
        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new UserNotFoundException("유저 조회 실패"));

        PostUpdate postUpdate = postUpdateRequest.getPostUpdate();
        Post updatedPost = foundPost.update(postUpdate);
        postRepository.save(updatedPost);

        // PostBlock 수정
        postBlockRepository.deleteAllByPostId(postId);  // 이미 있던 게시글 블록 삭제

        List<PostBlockCreate> postBlockCreateList = postUpdateRequest.getPostBlockCreateList();
        List<PostBlock> newPostBlockList = postBlockCreateList.stream()
                .map(postBlockCreate -> PostBlock.create(postBlockCreate, postId))
                .toList();
        postBlockRepository.saveAll(newPostBlockList);
    }

    public Page<PostListResponse> findAll(PostSearchCond postSearchCond, Pageable pageable) {
        return postRepository.findAll(postSearchCond, pageable).map(PostListResponse::from);
    }

    public PostPostBlockResponse findByIdWithPostBlock(Long postId) {
        // Post 조회 및 변환
        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글 조회 실패"));
        PostResponse postResponse = PostResponse.from(foundPost);

        // PostBlockList 조회 및 변환
        List<PostBlock> foundPostBlockList = postBlockRepository.findByPostId(foundPost.getId());
        List<PostBlockResponse> postBlockResponseList = foundPostBlockList.stream()
                .map(PostBlockResponse::from)
                .toList();

        return new PostPostBlockResponse(postResponse, postBlockResponseList);
    }
}

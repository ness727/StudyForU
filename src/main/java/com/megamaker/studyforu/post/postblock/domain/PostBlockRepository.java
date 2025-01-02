package com.megamaker.studyforu.post.postblock.domain;

import com.megamaker.studyforu.post.postblock.domain.PostBlock;

import java.util.List;

public interface PostBlockRepository {
    void save(PostBlock postBlock);

    void saveAll(List<PostBlock> postBlockList);

    List<PostBlock> findByPostId(Long postId);

    void deleteAllByPostId(Long postId);
}

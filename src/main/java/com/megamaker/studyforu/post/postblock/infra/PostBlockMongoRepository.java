package com.megamaker.studyforu.post.postblock.infra;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostBlockMongoRepository extends MongoRepository<PostBlockDoc, String> {
    List<PostBlockDoc> findByPostId(Long postId);

    void deleteAllByPostId(Long postId);
}

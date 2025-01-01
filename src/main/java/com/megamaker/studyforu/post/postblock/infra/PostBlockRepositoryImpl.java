package com.megamaker.studyforu.post.postblock.infra;

import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.PostBlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostBlockRepositoryImpl implements PostBlockRepository {
    private final PostBlockMongoRepository postBlockMongoRepository;

    @Override
    public void save(PostBlock postBlock) {
        postBlockMongoRepository.save(PostBlockDoc.from(postBlock));
    }

    @Override
    public List<PostBlock> findByPostId(Long postId) {
        return postBlockMongoRepository.findByPostId(postId).stream()
                .map(PostBlockDoc::toModel)
                .toList();
    }
}

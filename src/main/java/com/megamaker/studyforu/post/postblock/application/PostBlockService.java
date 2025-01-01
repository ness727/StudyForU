package com.megamaker.studyforu.post.postblock.application;

import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.PostBlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostBlockService {
    private final PostBlockRepository postBlockRepository;

    public void save(PostBlock postBlock) {
        postBlockRepository.save(postBlock);
    }

}

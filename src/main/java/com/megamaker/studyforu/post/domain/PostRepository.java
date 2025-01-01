package com.megamaker.studyforu.post.domain;

import com.megamaker.studyforu.post.domain.dto.PostSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Long save(Post post);

    Page<Post> findAll(PostSearchCond postSearchCond, Pageable pageable);

    Optional<Post> findById(Long id);
}

package com.megamaker.studyforu.mock;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.PostRepository;
import com.megamaker.studyforu.post.domain.dto.PostSearchCond;
import com.megamaker.studyforu.post.infra.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakePostRepository implements PostRepository {
    private final List<Post> dataList = new ArrayList<>();
    private long id;

    @Override
    public Long save(Post post) {
        Optional<Post> foundPost = dataList.stream()
                .filter(data -> data.getId().equals(post.getId()))
                .findAny();
        // 동인 id 게시글이면 저장 전 미리 삭제
        foundPost.ifPresent(dataList::remove);

        Post newPost = Post.builder()
                .id(foundPost.isPresent() ? foundPost.get().getId() : increaseIdAndGet())
                .userInfo(post.getUserInfo())
                .categoryId(post.getCategoryId())
                .title(post.getTitle())
                .body(post.getBody())
                .price(post.getPrice())
                .likes(post.getLikes())
                .status(post.getStatus())
                .build();
        dataList.add(newPost);
        return newPost.getId();
    }

    @Override
    public Page<Post> findAll(PostSearchCond postSearchCond, Pageable pageable) {
        return new PageImpl<>(dataList, pageable, dataList.size());
    }

    @Override
    public Optional<Post> findById(Long id) {
        return dataList.stream()
                .filter(post -> post.getId().equals(id))
                .findAny();
    }

    private long increaseIdAndGet() {
        return ++id;
    }
}

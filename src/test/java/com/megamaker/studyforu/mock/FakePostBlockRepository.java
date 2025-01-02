package com.megamaker.studyforu.mock;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.PostRepository;
import com.megamaker.studyforu.post.domain.dto.PostSearchCond;
import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.PostBlockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakePostBlockRepository implements PostBlockRepository {
    private final List<PostBlock> dataList = new ArrayList<>();
    private long id;

    @Override
    public void save(PostBlock postBlock) {
        dataList.add(postBlock);
    }

    @Override
    public void saveAll(List<PostBlock> postBlockList) {
        dataList.addAll(postBlockList);
    }

    @Override
    public List<PostBlock> findByPostId(Long postId) {
        return dataList.stream()
                .filter(postBlock -> postBlock.getPostId().equals(postId))
                .toList();
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        List<PostBlock> foundPostBlockList = findByPostId(postId);
        dataList.removeAll(foundPostBlockList);
    }
}

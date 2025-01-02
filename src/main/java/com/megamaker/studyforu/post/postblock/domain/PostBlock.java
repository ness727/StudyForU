package com.megamaker.studyforu.post.postblock.domain;

import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockCreate;
import com.megamaker.studyforu.post.postblock.domain.vo.BlockType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostBlock {
    private final String id;
    private final Long postId;
    private final Integer index;
    private final BlockType blockType;
    private final String body;

    public static PostBlock create(PostBlockCreate postBlockCreate, Long postId) {
        return PostBlock.builder()
                .postId(postId)
                .index(postBlockCreate.getIndex())
                .blockType(postBlockCreate.getBlockType())
                .body(postBlockCreate.getBody())
                .build();
    }
}

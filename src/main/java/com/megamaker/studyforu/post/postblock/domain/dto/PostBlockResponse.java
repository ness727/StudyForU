package com.megamaker.studyforu.post.postblock.domain.dto;

import com.megamaker.studyforu.post.domain.dto.PostResponse;
import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.vo.BlockType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostBlockResponse {
    private final Integer index;
    private final BlockType blockType;
    private final String body;

    public static PostBlockResponse from(PostBlock postBlock) {
        return new PostBlockResponse(postBlock.getIndex(), postBlock.getBlockType(), postBlock.getBody());
    }
}

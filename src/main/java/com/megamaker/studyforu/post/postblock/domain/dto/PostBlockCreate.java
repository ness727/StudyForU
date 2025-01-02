package com.megamaker.studyforu.post.postblock.domain.dto;

import com.megamaker.studyforu.post.postblock.domain.vo.BlockType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostBlockCreate {
    private final Integer index;
    private final BlockType blockType;
    private final String body;
}

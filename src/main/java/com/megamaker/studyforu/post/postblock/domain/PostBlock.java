package com.megamaker.studyforu.post.postblock.domain;

import com.megamaker.studyforu.post.postblock.domain.vo.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostBlock {
    String id;
    Long postId;
    Integer index;
    Type type;
    String body;
}

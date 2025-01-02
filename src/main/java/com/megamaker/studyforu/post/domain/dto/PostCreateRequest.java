package com.megamaker.studyforu.post.domain.dto;

import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostCreateRequest {
    private PostCreate postCreate;
    private List<PostBlockCreate> postBlockCreateList;
}

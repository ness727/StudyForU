package com.megamaker.studyforu.post.domain.dto;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.dto.PostBlockResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostPostBlockResponse {
    private PostResponse post;
    private List<PostBlockResponse> postBlock;
}

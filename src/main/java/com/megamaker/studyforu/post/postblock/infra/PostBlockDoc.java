package com.megamaker.studyforu.post.postblock.infra;

import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.vo.BlockType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "post_blocks")
public class PostBlockDoc {
    @Id
    String id;
    Long postId;
    Integer index;
    BlockType blockType;
    String body;

    public PostBlock toModel() {
        return new PostBlock(id, postId, index, blockType, body);
    }

    public static PostBlockDoc from(PostBlock postBlock) {
        return new PostBlockDoc(postBlock.getId(), postBlock.getPostId(), postBlock.getIndex(),
                postBlock.getBlockType(), postBlock.getBody());
    }
}

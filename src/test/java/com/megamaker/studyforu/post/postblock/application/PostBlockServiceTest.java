package com.megamaker.studyforu.post.postblock.application;

import com.megamaker.studyforu.mock.FakePostBlockRepository;
import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.vo.BlockType;
import com.megamaker.studyforu.post.postblock.infra.PostBlockRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

//@TestPropertySource(locations = "/application.yaml")
//@Import({PostBlockService.class, PostBlockRepositoryImpl.class})
//@DataMongoTest
class PostBlockServiceTest {
    private final PostBlockService postBlockService;

    public PostBlockServiceTest() {
        postBlockService = new PostBlockService(new FakePostBlockRepository());
    }

    @DisplayName("PostBlock 저장에 성공한다.")
    @Test
    void saveSuccess() {
        // given
        Long postId = 168L;
        Integer index = 0;
        BlockType blockType = BlockType.IMAGE;
        String body = "안녕하세요";
        PostBlock postBlock = new PostBlock(null, postId, index, blockType, body);

        // when
        postBlockService.save(postBlock);
        List<PostBlock> postBlockList = postBlockService.findByPostId(postId);

        // then
        PostBlock postBlock0 = postBlockList.get(0);
        Assertions.assertThat(postBlock0.getPostId()).isEqualTo(postId);
        Assertions.assertThat(postBlock0.getIndex()).isEqualTo(index);
        Assertions.assertThat(postBlock0.getBlockType()).isEqualTo(blockType);
        Assertions.assertThat(postBlock0.getBody()).isEqualTo(body);
    }
}
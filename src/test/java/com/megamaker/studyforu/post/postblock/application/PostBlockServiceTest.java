package com.megamaker.studyforu.post.postblock.application;

import com.megamaker.studyforu.post.postblock.domain.PostBlock;
import com.megamaker.studyforu.post.postblock.domain.vo.Type;
import com.megamaker.studyforu.post.postblock.infra.PostBlockRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import({PostBlockService.class, PostBlockRepositoryImpl.class})
@DataMongoTest
class PostBlockServiceTest {
    @Autowired
    PostBlockService postBlockService;

    @DisplayName("PostBlock 저장에 성공한다.")
    @Test
    void saveSuccess() {
        // given
        PostBlock postBlock = new PostBlock(null, 0L, 0, Type.IMAGE, "ㅁㄴㄹㄴㅇ");

        // when
        postBlockService.save(postBlock);

        // then

    }
}
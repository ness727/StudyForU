package com.megamaker.studyforu.post.infra;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.PostRepository;
import com.megamaker.studyforu.post.domain.dto.PostSearchCond;
import com.megamaker.studyforu.post.domain.vo.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(PostRepositoryImpl.class)
@SpringBootTest
class PostRepositoryImplTest {
    @Autowired
    PostRepository postRepository;

    List<Post> postList = new ArrayList<>();

    @BeforeEach
    void init() {
        Post post1 = Post.builder()
                .userId(1L)
                .categoryId(1)
                .title("Test title1")
                .body("안녕하세요1")
                .price(100)
                .likes(1)
                .status(Status.PRIVATE)
                .build();
        Post post2 = Post.builder()
                .userId(2L)
                .categoryId(2)
                .title("Test title2")
                .body("안녕하세요2")
                .price(200)
                .likes(2)
                .status(Status.PUBLIC)
                .build();
        Post post3 = Post.builder()
                .userId(3L)
                .categoryId(3)
                .title("Test title3")
                .body("안녕하세요3")
                .price(300)
                .likes(3)
                .status(Status.SECRET)
                .build();
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);
    }

    @AfterEach
    void clear() {
        postList.clear();
    }

    @DisplayName("post 저장 및 단건 조회에 성공한다")
    @Test
    void saveAndFindByIdSuccess() {
        // given
        Post post = Post.builder()
                .userId(2L)
                .categoryId(53)
                .title("Test title")
                .body("svjipal dsjgiagl sdgjfsl")
                .price(200)
                .likes(2)
                .status(Status.PUBLIC)
                .build();

        // when
        Long postEntityId = postRepository.save(post);
        Post foundPost = postRepository.findById(postEntityId).orElseThrow();

        // then
        assertThat(foundPost.getId()).isNotNull();
        assertThat(foundPost.getUserId()).isEqualTo(post.getUserId());
        assertThat(foundPost.getCategoryId()).isEqualTo(post.getCategoryId());
        assertThat(foundPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(foundPost.getBody()).isEqualTo(post.getBody());
        assertThat(foundPost.getPrice()).isEqualTo(post.getPrice());
        assertThat(foundPost.getLikes()).isEqualTo(post.getLikes());
        assertThat(foundPost.getStatus()).isEqualTo(post.getStatus());
    }

    @DisplayName("title 내림차순으로 post 리스트 조회에 성공한다")
    @Test
    void findAllSuccess() {
        // given
        Post post1 = postList.get(0);
        Post post2 = postList.get(1);
        Post post3 = postList.get(2);

        PostSearchCond postSearchCond = new PostSearchCond(null, null, null);
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "title");

        // when
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        Page<Post> foundPostList = postRepository.findAll(postSearchCond, pageRequest);

        // then
        assertThat(foundPostList.getContent().size()).isEqualTo(2);

        Post foundPost = foundPostList.getContent().get(1);
        assertThat(foundPost.getId()).isNotNull();
        assertThat(foundPost.getUserId()).isEqualTo(post2.getUserId());
        assertThat(foundPost.getCategoryId()).isEqualTo(post2.getCategoryId());
        assertThat(foundPost.getTitle()).isEqualTo(post2.getTitle());
        assertThat(foundPost.getBody()).isEqualTo(post2.getBody());
        assertThat(foundPost.getPrice()).isEqualTo(post2.getPrice());
        assertThat(foundPost.getLikes()).isEqualTo(post2.getLikes());
        assertThat(foundPost.getStatus()).isEqualTo(post2.getStatus());
    }

    @DisplayName("title 내림차순으로 post 리스트 검색에 성공한다")
    @Test
    void findAllSearchSuccess() {
        // given
        Post post1 = postList.get(0);
        Post post2 = postList.get(1);
        Post post3 = postList.get(2);

        /*
            title에 2를 포함한 post 찾기
         */
        PostSearchCond postSearchCond = new PostSearchCond("2", null, null);
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "title");

        // when
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        Page<Post> foundPostList = postRepository.findAll(postSearchCond, pageRequest);

        // then
        assertThat(foundPostList.getContent().size()).isEqualTo(1);

        Post foundPost = foundPostList.getContent().get(0);
        assertThat(foundPost.getId()).isNotNull();
        assertThat(foundPost.getUserId()).isEqualTo(post2.getUserId());
        assertThat(foundPost.getCategoryId()).isEqualTo(post2.getCategoryId());
        assertThat(foundPost.getTitle()).isEqualTo(post2.getTitle());
        assertThat(foundPost.getBody()).isEqualTo(post2.getBody());
        assertThat(foundPost.getPrice()).isEqualTo(post2.getPrice());
        assertThat(foundPost.getLikes()).isEqualTo(post2.getLikes());
        assertThat(foundPost.getStatus()).isEqualTo(post2.getStatus());
    }
}
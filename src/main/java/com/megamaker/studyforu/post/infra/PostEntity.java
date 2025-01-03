package com.megamaker.studyforu.post.infra;

import com.megamaker.studyforu.common.infra.BaseDateTime;
import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.vo.Status;
import com.megamaker.studyforu.post.domain.vo.UserInfo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "posts")
@Entity
public class PostEntity extends BaseDateTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "writer_name")
    private String writerName;

    @Column(name = "category_id")
    private Integer categoryId;

    private String title;

    private String body;

    private Integer price;

    private Integer likes;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Post toModel() {
        UserInfo userInfo = new UserInfo(userId, writerName);
        return Post.builder()
                .id(id)
                .userInfo(userInfo)
                .categoryId(categoryId)
                .title(title)
                .body(body)
                .price(price)
                .likes(likes)
                .status(status)
                .build();
    }

    public static PostEntity from(Post post) {
        UserInfo userInfo = post.getUserInfo();
        return PostEntity.builder()
                .id(post.getId())
                .userId(userInfo.getUserId())
                .writerName(userInfo.getName())
                .categoryId(post.getCategoryId())
                .title(post.getTitle())
                .body(post.getBody())
                .price(post.getPrice())
                .likes(post.getLikes())
                .status(post.getStatus())
                .build();
    }
}

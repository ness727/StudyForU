package com.megamaker.studyforu.post.infra;

import com.megamaker.studyforu.post.domain.Post;
import com.megamaker.studyforu.post.domain.PostRepository;
import com.megamaker.studyforu.post.domain.dto.PostSearchCond;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.megamaker.studyforu.post.infra.QPostEntity.postEntity;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Long save(Post post) {
        PostEntity postEntity = PostEntity.from(post);
        entityManager.persist(postEntity);
        return postEntity.getId();
    }

    @Override
    public Page<Post> findAll(PostSearchCond postSearchCond, Pageable pageable) {
        BooleanBuilder builder = getCondResult(postSearchCond);

        int totalSize = queryFactory
                .selectFrom(postEntity)
                .where(builder)
                .fetch().size();

        List<PostEntity> foundProblemListEntity = queryFactory
                .selectFrom(postEntity)
                .where(builder)
                .orderBy(getOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(
                foundProblemListEntity.stream()
                        .map(PostEntity::toModel)
                        .toList(),
                pageable, totalSize);
    }

    // 검색 로직
    private static BooleanBuilder getCondResult(PostSearchCond postSearchCond) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        String title = postSearchCond.getTitle();
        Integer minPrice = postSearchCond.getMinPrice();
        Integer maxPrice = postSearchCond.getMaxPrice();

        // 제목으로 검색
        // 해당 글자 포함으로 검색
        if (StringUtils.hasText(title)) {
            booleanBuilder.and(postEntity.title.contains(title));
        }

        // 최소 가격보다 크거나 같은 post 검색
        if (minPrice != null) {
            booleanBuilder.and(postEntity.price.goe(minPrice));
        }

        // 최대 가격보다 작거나 같은 post 검색
        if (maxPrice != null) {
            booleanBuilder.and(postEntity.price.loe(maxPrice));
        }

        return booleanBuilder;
    }

    // 정렬 로직
    private static OrderSpecifier[] getOrderSpecifiers(Pageable pageable) {
        return pageable.getSort().stream()
                .map((Sort.Order order) -> {
                    Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                    Expression<?> expression = switch (order.getProperty()) {
                        case "title" -> postEntity.title;
                        case "price" -> postEntity.price;
                        default -> null;
                    };
                    return new OrderSpecifier(direction, expression);
                }).toArray(OrderSpecifier[]::new);
    }


    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(entityManager.find(PostEntity.class, id)).map(PostEntity::toModel);
    }
}

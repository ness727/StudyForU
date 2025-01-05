package com.megamaker.studyforu.category.infra;

import com.megamaker.studyforu.category.domain.Category;
import com.megamaker.studyforu.category.domain.dto.CategoryView;
import com.megamaker.studyforu.common.infra.BaseDateTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "categories")
@Entity
public class CategoryEntity extends BaseDateTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    private Integer level;

    private String name;

    public Category toModel() {
        return Category.builder()
                .id(id)
                .parentId(parentId)
                .name(name)
                .level(level)
                .build();
    }

    public static CategoryEntity from(Category category) {
        return CategoryEntity.builder()
                .id(category.getId())
                .parentId(category.getParentId())
                .name(category.getName())
                .level(category.getLevel())
                .build();
    }

    public CategoryView toView(List<CategoryView> categoryList) {
        return CategoryView.builder()
                .id(id)
                .parentId(parentId)
                .name(name)
                .level(level)
                .childCategoryList(categoryList)
                .build();
    }
}

package com.megamaker.studyforu.category.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
public class CategoryView {
    private final Long id;
    private final Long parentId;
    private final List<CategoryView> childCategoryList;
    private final String name;
}

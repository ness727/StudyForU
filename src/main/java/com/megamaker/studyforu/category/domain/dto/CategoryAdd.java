package com.megamaker.studyforu.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryAdd {
    private final Long parentId;
    private final Integer level;
    private final String name;
    private final String description;  // admin에게 보여줄 새로운 카테고리 예상 구조
}

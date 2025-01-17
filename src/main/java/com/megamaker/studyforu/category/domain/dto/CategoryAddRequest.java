package com.megamaker.studyforu.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryAddRequest {
    private final Long parentId;
    private final Integer parentLevel;
    private final String name;
}

package com.megamaker.studyforu.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryAddRequest {
    private final Long parentId;
    private final Integer level;
    private final String name;
}

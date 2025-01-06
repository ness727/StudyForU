package com.megamaker.studyforu.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategorySaveRequest {
    private String key;
    private Long parentId;
    private Integer level;
    private String name;
}

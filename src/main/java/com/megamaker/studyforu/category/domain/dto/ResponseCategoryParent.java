package com.megamaker.studyforu.category.domain.dto;

import com.megamaker.studyforu.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCategoryParent {
    private final Long id;
    private final String name;

    public static ResponseCategoryParent from(Category category) {
        return new ResponseCategoryParent(category.getId(), category.getName());
    }
}

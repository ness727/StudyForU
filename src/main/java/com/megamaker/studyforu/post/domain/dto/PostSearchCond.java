package com.megamaker.studyforu.post.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSearchCond {
    private String title;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer category;
}

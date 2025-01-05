package com.megamaker.studyforu.category.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
public class Category {
    private final Long id;
    private final Long parentId;
    private final String name;
    private final Integer level;
}

package com.megamaker.studyforu.category.domain;

import com.megamaker.studyforu.category.domain.dto.CategoryView;

import java.util.List;

public interface CategoryRepository {
    Long save(Category category);

    List<CategoryView> findAll();

    List<Category> findAllParent();

    void delete(Long id);
}

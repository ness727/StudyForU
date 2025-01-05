package com.megamaker.studyforu.category.domain;

import com.megamaker.studyforu.category.domain.dto.CategoryView;

import java.util.List;

public interface CategoryRepository {
    Long save(Category category);

    List<CategoryView> findAll();

    List<Category> findAllParent();

    List<CategoryView> findOneTreeById(Long id);

    void delete(Long id);
}

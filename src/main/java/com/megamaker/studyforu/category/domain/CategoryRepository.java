package com.megamaker.studyforu.category.domain;

import com.megamaker.studyforu.category.domain.dto.CategoryView;
import com.megamaker.studyforu.category.infra.CategoryEntity;

import java.util.List;

public interface CategoryRepository {
    Long save(Category category);

    List<CategoryView> findAll();

    List<Category> findAllParent();

    List<Category> findRootToLeafByLeafId(Long leafId);

    List<CategoryView> findTreeByRootId(Long id);

    void delete(Long id);
}

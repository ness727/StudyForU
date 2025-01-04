package com.megamaker.studyforu.category.domain;

import java.util.List;

public interface CategoryRepository {
    Long save(Category category);

    List<CategoryView> findAll();

    void delete(Long id);
}

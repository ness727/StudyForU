package com.megamaker.studyforu.category.application;

import com.megamaker.studyforu.category.domain.Category;
import com.megamaker.studyforu.category.domain.CategoryRepository;
import com.megamaker.studyforu.category.domain.dto.CategoryView;
import com.megamaker.studyforu.category.domain.dto.ResponseCategoryParent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Long add(Category category) {
        return categoryRepository.save(category);
    }

    public List<CategoryView> findAll() {
        return categoryRepository.findAll();
    }

    public List<ResponseCategoryParent> findAllParent() {
        return categoryRepository.findAllParent().stream()
                .map(ResponseCategoryParent::from)
                .toList();
    }

    public List<CategoryView> findOneTreeById(Long id) {
        return categoryRepository.findOneTreeById(id);
    }

    public void delete(Long id) {
        categoryRepository.delete(id);
    }
}

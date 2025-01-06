package com.megamaker.studyforu.category.application;

import com.megamaker.studyforu.category.domain.Category;
import com.megamaker.studyforu.category.domain.CategoryRepository;
import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.category.domain.dto.CategoryView;
import com.megamaker.studyforu.category.domain.dto.ResponseCategoryParent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void request(CategoryAddRequest categoryAddRequest) {
        Category.request(categoryAddRequest);
    }

    @Transactional
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

    @Transactional
    public void delete(Long id) {
        categoryRepository.delete(id);
    }
}

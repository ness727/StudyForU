package com.megamaker.studyforu.category.application;

import com.megamaker.studyforu.category.domain.Category;
import com.megamaker.studyforu.category.domain.CategoryRepository;
import com.megamaker.studyforu.category.domain.dto.AddRequestUserInfo;
import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.category.domain.dto.CategoryView;
import com.megamaker.studyforu.category.domain.dto.ResponseCategoryParent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void addRequest(CategoryAddRequest addRequest, AddRequestUserInfo userInfo) {
        List<Category> foundCategoryList = categoryRepository.findRootToLeafByLeafId(addRequest.getParentId());
        Category.addRequest(addRequest, userInfo, foundCategoryList);  // 카테고리 추가 요청
    }

    @Transactional
    public Long save(Category category) {
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
        return categoryRepository.findTreeByRootId(id);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.delete(id);
    }
}

package com.megamaker.studyforu.category.application;

import com.megamaker.studyforu.category.domain.CategoryAddKeyStore;
import com.megamaker.studyforu.category.domain.Category;
import com.megamaker.studyforu.category.domain.CategoryRepository;
import com.megamaker.studyforu.category.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryAddKeyStore categoryAddKeyStore;

    public void addRequest(CategoryAddRequest addRequest, AddRequestUserInfo userInfo) {
        List<Category> foundCategoryList = categoryRepository.findRootToLeafByLeafId(addRequest.getParentId());
        Category.addRequest(addRequest, userInfo, foundCategoryList, categoryAddKeyStore);  // 카테고리 추가 요청
    }

    @Transactional
    public boolean save(CategorySaveRequest request) {
        // 처음 저장을 시도할 때
        if (categoryAddKeyStore.checkContainsKey(request.getKey())) {
            Category category = Category.builder()
                    .parentId(request.getParentId())
                    .level(request.getLevel())
                    .name(request.getName())
                    .build();
            categoryRepository.save(category);  // 새로운 카테고리 저장

            // 두 번 저장 못하도록 키 저장소에서 삭제
            categoryAddKeyStore.delete(request.getKey());
            return true;
        } else return false;
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

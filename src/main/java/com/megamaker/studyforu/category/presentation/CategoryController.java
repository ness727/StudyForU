package com.megamaker.studyforu.category.presentation;

import com.megamaker.studyforu.category.application.CategoryService;
import com.megamaker.studyforu.category.domain.dto.CategoryView;
import com.megamaker.studyforu.category.domain.dto.ResponseCategoryParent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

//    public void save() {
//
//    }

    @GetMapping("/main")
    public List<ResponseCategoryParent> findAllParent() {
        return categoryService.findAllParent();
    }

    @GetMapping("/all")
    public List<CategoryView> findAll() {
        return categoryService.findAll();
    }
}

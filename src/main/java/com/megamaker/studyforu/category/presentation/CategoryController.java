package com.megamaker.studyforu.category.presentation;

import com.megamaker.studyforu.category.application.CategoryService;
import com.megamaker.studyforu.category.domain.dto.*;
import com.megamaker.studyforu.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/check")
    public ResponseEntity save(@ModelAttribute CategorySaveRequest categorySaveRequest) {
        boolean result = categoryService.save(categorySaveRequest);
        if (result) return ResponseEntity.ok("카테고리 저장에 성공했습니다.");
        else return ResponseEntity.badRequest().body("중복 요청입니다.");
    }

    @GetMapping
    public List<ResponseCategoryParent> findAllParent() {
        return categoryService.findAllParent();
    }

    @GetMapping("/all")
    public List<CategoryView> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public List<CategoryView> findOneTree(@PathVariable Long id) {
        return categoryService.findOneTreeById(id);
    }

    @PostMapping("/request")
    public ResponseEntity request(@ModelAttribute CategoryAddRequest request, Authentication auth) {
        User user = (User) auth.getPrincipal();
        AddRequestUserInfo addRequestUserInfo = new AddRequestUserInfo(user.getUsername(), user.getId());
        categoryService.addRequest(request, addRequestUserInfo);
        return ResponseEntity.ok("요청 메일 발송에 성공했습니다.");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}

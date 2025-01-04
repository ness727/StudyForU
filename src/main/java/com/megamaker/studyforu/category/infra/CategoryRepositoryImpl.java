package com.megamaker.studyforu.category.infra;

import com.megamaker.studyforu.category.domain.Category;
import com.megamaker.studyforu.category.domain.CategoryRepository;
import com.megamaker.studyforu.category.domain.CategoryView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Long save(Category category) {
        CategoryEntity categoryEntity = CategoryEntity.from(category);
        categoryJpaRepository.save(categoryEntity);
        return categoryEntity.getId();
    }

    @Override
    public List<CategoryView> findAll() {
        List<CategoryEntity> all = categoryJpaRepository.findAllByOrderByIdAsc();
        Map<Long, CategoryView> categoryMap = new HashMap<>();
        List<CategoryView> result = new ArrayList<>();

        all.forEach(entity -> {
            CategoryView category = entity.toView(new ArrayList<>());
            categoryMap.put(entity.getId(), category);

            // 부모 노드일 때
            if (entity.getParentId() == null) {
                result.add(category);
            } else {  // 자식 노드일 때
                CategoryView parentNode = categoryMap.get(entity.getParentId());
                parentNode.getChildCategoryList().add(category);
            }
        });
        return result;
    }

    @Override
    public void delete(Long id) {
        categoryJpaRepository.deleteById(id);
    }
}

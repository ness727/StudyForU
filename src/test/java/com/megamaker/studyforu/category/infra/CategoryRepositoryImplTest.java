package com.megamaker.studyforu.category.infra;

import com.megamaker.studyforu.category.domain.Category;
import com.megamaker.studyforu.category.domain.dto.CategoryView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("localdb")
@Import(CategoryRepositoryImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class CategoryRepositoryImplTest {
    @Autowired CategoryRepositoryImpl categoryRepositoryImpl;

    Category parent1, parent2;
    Category child1, child2, child3;
    Category childChild1;

    @BeforeEach
    void init() {
        parent1 = Category.builder()
                .parentId(null)
                .name("parent1")
                .level(0)
                .build();
        parent2 = Category.builder()
                .parentId(null)
                .name("parent2")
                .level(0)
                .build();
        Long parentId = categoryRepositoryImpl.save(parent1);
        categoryRepositoryImpl.save(parent2);

        child1 = Category.builder()
                .parentId(parentId)
                .name("child1")
                .level(1)
                .build();
        child2 = Category.builder()
                .parentId(parentId)
                .name("child2")
                .level(1)
                .build();
        child3 = Category.builder()
                .parentId(parentId)
                .name("child3")
                .level(1)
                .build();
        Long child1Id = categoryRepositoryImpl.save(child1);
        categoryRepositoryImpl.save(child2);
        categoryRepositoryImpl.save(child3);

        childChild1 = Category.builder()
                .parentId(child1Id)
                .name("childChild1")
                .level(2)
                .build();
        categoryRepositoryImpl.save(childChild1);
    }

    @DisplayName("Category 저장에 성공한다.")
    @Test
    void saveSuccess() {
        // given
        final int parentSize = 2;

        // when
        List<CategoryView> all = categoryRepositoryImpl.findAll();
        System.out.println(all);
        CategoryView parent1View = all.get(0);
        CategoryView child1View = all.get(0).getChildCategoryList().get(0);

        // then
        assertThat(all.size()).isEqualTo(parentSize);

        /*
            부모 노드
         */
        assertThat(parent1View.getId()).isNotNull();
        assertThat(parent1View.getParentId()).isEqualTo(parent1.getParentId());
        assertThat(parent1View.getName()).isEqualTo(parent1.getName());
        assertThat(parent1View.getLevel()).isEqualTo(parent1.getLevel());
        assertThat(parent1View.getChildCategoryList().size()).isEqualTo(3);

        /*
            첫 번째 자식 노드
         */
        assertThat(child1View.getId()).isNotNull();
        assertThat(child1View.getParentId()).isEqualTo(child1.getParentId());
        assertThat(child1View.getName()).isEqualTo(child1.getName());
        assertThat(child1View.getLevel()).isEqualTo(child1.getLevel());
        assertThat(child1View.getChildCategoryList().size()).isEqualTo(1);
    }

    @DisplayName("부모 이전에 자식이 지워지면 삭제 실패한다.")
    @Test
    void deleteFail() {
        // given

        // when
        List<CategoryView> all = categoryRepositoryImpl.findAll();
        CategoryView child1View = all.get(0).getChildCategoryList().get(0);
        CategoryView child2View = all.get(0).getChildCategoryList().get(1);

        // then
        /*
            자식이 없는 노드는 삭제 가능
         */
        Assertions.assertDoesNotThrow(() -> {
            categoryRepositoryImpl.delete(child2View.getId());
            categoryRepositoryImpl.findAll();
        });

        /*
            자식보다 부모 노드를 먼저 삭제하면 외래키 관련 예외 발생
         */
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            categoryRepositoryImpl.delete(child1View.getId());
            categoryRepositoryImpl.findAll();
        });
    }

    @DisplayName("부모 카테고리만 조회에 성공한다")
    @Test
    void findParentNodesSuccess() {
        // given
        final int parentSize = 2;

        // when
        List<Category> allParentList = categoryRepositoryImpl.findAllParent();
        Category parent1Category = allParentList.get(0);
        Category parent2Category = allParentList.get(1);

        // then
        assertThat(allParentList.size()).isEqualTo(parentSize);

        assertThat(parent1Category.getParentId()).isNull();
        assertThat(parent1Category.getId()).isNotNull();
        assertThat(parent1Category.getName()).isEqualTo(parent1.getName());
        assertThat(parent1Category.getLevel()).isEqualTo(parent1.getLevel());

        assertThat(parent2Category.getParentId()).isNull();
        assertThat(parent2Category.getId()).isNotNull();
        assertThat(parent2Category.getName()).isEqualTo(parent2.getName());
        assertThat(parent2Category.getLevel()).isEqualTo(parent2.getLevel());
    }

    @DisplayName("특정 카테고리 트리만 조회에 성공한다.")
    @Test
    void findOneCategoryTreeSuccess() {
        // given

        // when
        List<Category> allParent = categoryRepositoryImpl.findAllParent();
        Long parent1Id = allParent.get(0).getId();
        List<CategoryView> oneTreeById = categoryRepositoryImpl.findOneTreeById(parent1Id);

        CategoryView parent1View = oneTreeById.get(0);

        // then
        assertThat(oneTreeById.size()).isEqualTo(1);
        assertThat(parent1View.getChildCategoryList().size()).isEqualTo(3);
        assertThat(parent1View.getChildCategoryList().get(0).getChildCategoryList().size()).isEqualTo(1);

        assertThat(parent1View.getId()).isNotNull();
        assertThat(parent1View.getParentId()).isNull();
        assertThat(parent1View.getName()).isEqualTo(parent1.getName());
        assertThat(parent1View.getLevel()).isEqualTo(parent1.getLevel());
    }

}
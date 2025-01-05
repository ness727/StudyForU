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
                .build();
        parent2 = Category.builder()
                .parentId(null)
                .name("parent2")
                .build();
        Long parentId = categoryRepositoryImpl.save(parent1);
        categoryRepositoryImpl.save(parent2);

        child1 = Category.builder()
                .parentId(parentId)
                .name("child1")
                .build();
        child2 = Category.builder()
                .parentId(parentId)
                .name("child2")
                .build();
        child3 = Category.builder()
                .parentId(parentId)
                .name("child3")
                .build();
        Long child1Id = categoryRepositoryImpl.save(child1);
        categoryRepositoryImpl.save(child2);
        categoryRepositoryImpl.save(child3);

        childChild1 = Category.builder()
                .parentId(child1Id)
                .name("childChild1")
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
        CategoryView categoryView0 = all.get(0);
        CategoryView categoryView1 = all.get(0).getChildCategoryList().get(0);

        // then
        assertThat(all.size()).isEqualTo(parentSize);

        /*
            부모 노드
         */
        assertThat(categoryView0.getId()).isNotNull();
        assertThat(categoryView0.getParentId()).isEqualTo(parent1.getParentId());
        assertThat(categoryView0.getName()).isEqualTo(parent1.getName());
        assertThat(categoryView0.getChildCategoryList().size()).isEqualTo(3);

        /*
            첫 번째 자식 노드
         */
        assertThat(categoryView1.getId()).isNotNull();
        assertThat(categoryView1.getParentId()).isEqualTo(child1.getParentId());
        assertThat(categoryView1.getName()).isEqualTo(child1.getName());
        assertThat(categoryView1.getChildCategoryList().size()).isEqualTo(1);
    }

    @DisplayName("부모 이전에 자식이 지워지면 삭제 실패한다.")
    @Test
    void deleteFail() {
        // given

        // when
        List<CategoryView> all = categoryRepositoryImpl.findAll();
        CategoryView categoryView1 = all.get(0).getChildCategoryList().get(0);
        CategoryView categoryView2 = all.get(0).getChildCategoryList().get(1);

        // then
        /*
            자식이 없는 노드는 삭제 가능
         */
        Assertions.assertDoesNotThrow(() -> {
            categoryRepositoryImpl.delete(categoryView2.getId());
            categoryRepositoryImpl.findAll();
        });

        /*
            자식보다 부모 노드를 먼저 삭제하면 외래키 관련 예외 발생
         */
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            categoryRepositoryImpl.delete(categoryView1.getId());
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
        Category category1 = allParentList.get(0);
        Category category2 = allParentList.get(1);

        // then
        assertThat(allParentList.size()).isEqualTo(parentSize);

        assertThat(category1.getParentId()).isNull();
        assertThat(category1.getId()).isNotNull();
        assertThat(category1.getName()).isEqualTo(parent1.getName());

        assertThat(category2.getParentId()).isNull();
        assertThat(category2.getId()).isNotNull();
        assertThat(category2.getName()).isEqualTo(parent2.getName());
    }

    @DisplayName("특정 카테고리 트리만 조회에 성공한다.")
    @Test
    void findOneCategoryTreeSuccess() {
        // given

        // when
        List<Category> allParent = categoryRepositoryImpl.findAllParent();
        Long parent1Id = allParent.get(0).getId();
        List<CategoryView> oneTreeById = categoryRepositoryImpl.findOneTreeById(parent1Id);

        CategoryView parent = oneTreeById.get(0);

        // then
        assertThat(oneTreeById.size()).isEqualTo(1);
        assertThat(parent.getChildCategoryList().size()).isEqualTo(3);
        assertThat(parent.getChildCategoryList().get(0).getChildCategoryList().size()).isEqualTo(1);

        assertThat(parent.getId()).isNotNull();
        assertThat(parent.getParentId()).isNull();
        assertThat(parent.getName()).isEqualTo(parent1.getName());
    }

}
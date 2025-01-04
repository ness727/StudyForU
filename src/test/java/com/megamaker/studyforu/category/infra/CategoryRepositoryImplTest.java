package com.megamaker.studyforu.category.infra;

import com.megamaker.studyforu.category.domain.Category;
import com.megamaker.studyforu.category.domain.CategoryView;
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

    Category parent;
    Category child1;
    Category child2;
    Category child3;
    Category childChild1;

    @BeforeEach
    void init() {
        parent = Category.builder()
                .parentId(null)
                .name("parent")
                .build();
        Long parentId = categoryRepositoryImpl.save(parent);

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

        // when
        List<CategoryView> all = categoryRepositoryImpl.findAll();
        System.out.println(all);
        CategoryView categoryView0 = all.get(0);
        CategoryView categoryView1 = all.get(0).getChildCategoryList().get(0);

        // then
        assertThat(all.size()).isEqualTo(1);

        /*
            부모 노드
         */
        assertThat(categoryView0.getId()).isNotNull();
        assertThat(categoryView0.getParentId()).isEqualTo(parent.getParentId());
        assertThat(categoryView0.getName()).isEqualTo(parent.getName());
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
        System.out.println(categoryView2);
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

}

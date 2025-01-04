package com.megamaker.studyforu.category.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByOrderByIdAsc();

    List<CategoryEntity> findAllByParentIdIsNull();
}

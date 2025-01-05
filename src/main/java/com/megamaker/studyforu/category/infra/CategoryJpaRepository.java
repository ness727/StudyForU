package com.megamaker.studyforu.category.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByOrderByIdAsc();

    List<CategoryEntity> findAllByParentIdIsNull();

    @Query(value = "WITH RECURSIVE ct AS (" +
            "    SELECT *" +
            "    FROM categories" +
            "    WHERE parent_id IS NULL AND id = :id" +
            "    UNION ALL" +
            "    SELECT c.*" +
            "    FROM categories c" +
            "    INNER JOIN ct on c.parent_id = ct.id" +
            ")" +
            "SELECT * FROM ct ORDER BY id;", nativeQuery = true)
    List<CategoryEntity> findOneTreeById(@Param("id") Long id);
}

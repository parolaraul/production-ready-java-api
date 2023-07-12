package com.parolaraul.recipeapi.repository;

import com.parolaraul.recipeapi.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Recipe entity.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findById(Long id);

    Page<Recipe> findAll(Pageable pageable);

    @Query("SELECT r FROM Recipe r JOIN FETCH r.ingredients WHERE r.id IN :ids")
    List<Recipe> findAllWithIngredientsByIds(@Param("ids") List<Long> ids);
}

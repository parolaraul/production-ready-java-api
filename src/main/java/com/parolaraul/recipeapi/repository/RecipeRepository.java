package com.parolaraul.recipeapi.repository;

import com.parolaraul.recipeapi.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Recipe entity.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @EntityGraph(attributePaths = {"ingredients"})
    Optional<Recipe> findById(Long id);

    @EntityGraph(attributePaths = {"ingredients"})
    Page<Recipe> findAll(Pageable pageable);
}

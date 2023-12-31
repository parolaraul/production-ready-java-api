package com.parolaraul.recipeapi.service;

import com.parolaraul.recipeapi.service.dto.RecipeDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.parolaraul.recipeapi.domain.Recipe}.
 */
public interface RecipeService {
    /**
     * Save a recipe.
     *
     * @param recipeDTO the entity to save.
     * @return the persisted entity.
     */
    RecipeDTO save(RecipeDTO recipeDTO);

    /**
     * Updates a recipe.
     *
     * @param recipeDTO the entity to update.
     * @return the persisted entity.
     */
    RecipeDTO update(RecipeDTO recipeDTO);

    /**
     * Get the "id" recipe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecipeDTO> findOne(Long id);

    /**
     * Delete the "id" recipe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

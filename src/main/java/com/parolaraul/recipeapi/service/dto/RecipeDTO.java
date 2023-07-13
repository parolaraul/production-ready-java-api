package com.parolaraul.recipeapi.service.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.parolaraul.recipeapi.domain.Recipe} entity.
 */
public record RecipeDTO(
        Long id,
        String category,
        Integer servings,
        String instructions,
        Set<IngredientDTO> ingredients) {

    public RecipeDTO {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
    }
}

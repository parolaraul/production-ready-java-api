package com.parolaraul.recipeapi.service.dto;

import com.parolaraul.recipeapi.domain.RecipeCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.parolaraul.recipeapi.domain.Recipe} entity.
 */
public record RecipeDTO(
        Long id,
        @ValueOfEnum(enumClass = RecipeCategory.class)
        String category,
        @Min(value = 1, message = "servings must be a positive integer")
        Integer servings,
        @NotBlank(message = "instructions cannot be blank")
        String instructions,
        Set<IngredientDTO> ingredients) {

    public RecipeDTO {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
    }
}

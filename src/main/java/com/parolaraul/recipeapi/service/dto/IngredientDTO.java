package com.parolaraul.recipeapi.service.dto;


import jakarta.validation.constraints.NotBlank;

/**
 * A DTO for the {@link com.parolaraul.recipeapi.domain.Ingredient} entity.
 */
public record IngredientDTO(Long id,
                            @NotBlank(message = "Name is required") String name) {
}

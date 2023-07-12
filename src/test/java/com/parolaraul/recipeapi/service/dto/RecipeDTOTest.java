package com.parolaraul.recipeapi.service.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        RecipeDTO recipeDTO1 = new RecipeDTO(1L, null, null, null, null);
        RecipeDTO recipeDTO2 = new RecipeDTO(2L, null, null, null, null);
        RecipeDTO recipeDTO3 = new RecipeDTO(1L, null, null, null, null);
        RecipeDTO recipeDTO4 = new RecipeDTO(null, null, null, null, null);
        assertThat(recipeDTO1).isNotEqualTo(recipeDTO2);
        assertThat(recipeDTO1).isNotEqualTo(recipeDTO4);
        assertThat(recipeDTO1).isEqualTo(recipeDTO3);
    }
}

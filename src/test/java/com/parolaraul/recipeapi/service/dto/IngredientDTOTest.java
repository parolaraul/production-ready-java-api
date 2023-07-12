package com.parolaraul.recipeapi.service.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        IngredientDTO ingredientDTO1 = new IngredientDTO(1L, null);
        IngredientDTO ingredientDTO2 = new IngredientDTO(2L, null);
        IngredientDTO ingredientDTO3 = new IngredientDTO(1L, null);
        IngredientDTO ingredientDTO4 = new IngredientDTO(null, null);
        assertThat(ingredientDTO1).isNotEqualTo(ingredientDTO2);
        assertThat(ingredientDTO1).isNotEqualTo(ingredientDTO4);
        assertThat(ingredientDTO1).isEqualTo(ingredientDTO3);
    }
}

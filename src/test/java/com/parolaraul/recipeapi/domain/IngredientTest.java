package com.parolaraul.recipeapi.domain;

import com.parolaraul.recipeapi.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ingredient.class);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ingredient1.getId());
        assertThat(ingredient1).isEqualTo(ingredient2);
        ingredient2.setId(2L);
        assertThat(ingredient1).isNotEqualTo(ingredient2);
        ingredient1.setId(null);
        assertThat(ingredient1).isNotEqualTo(ingredient2);
    }
}

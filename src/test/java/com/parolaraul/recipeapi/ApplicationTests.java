package com.parolaraul.recipeapi;

import com.parolaraul.recipeapi.repository.IngredientRepository;
import com.parolaraul.recipeapi.repository.RecipeRepository;
import com.parolaraul.recipeapi.service.IngredientService;
import com.parolaraul.recipeapi.service.RecipeService;
import com.parolaraul.recipeapi.service.mapper.IngredientMapper;
import com.parolaraul.recipeapi.service.mapper.RecipeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationTests {


    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private IngredientMapper ingredientMapper;


    @Test
    void contextLoads() {
        assertNotNull(recipeService);
        assertNotNull(ingredientService);
        assertNotNull(recipeRepository);
        assertNotNull(ingredientRepository);
        assertNotNull(recipeMapper);
        assertNotNull(ingredientMapper);
    }

}

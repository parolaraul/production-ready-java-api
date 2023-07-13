package com.parolaraul.recipeapi.rest;

import com.parolaraul.recipeapi.IntegrationTest;
import com.parolaraul.recipeapi.TestUtil;
import com.parolaraul.recipeapi.domain.Ingredient;
import com.parolaraul.recipeapi.domain.Recipe;
import com.parolaraul.recipeapi.domain.RecipeCategory;
import com.parolaraul.recipeapi.repository.IngredientRepository;
import com.parolaraul.recipeapi.repository.RecipeRepository;
import com.parolaraul.recipeapi.service.dto.RecipeDTO;
import com.parolaraul.recipeapi.service.mapper.RecipeMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RecipeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class RecipeResourceIT {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String APIKey = "test";

    private static final RecipeCategory DEFAULT_VEGETARIAN = RecipeCategory.nonvegetarian;
    private static final RecipeCategory UPDATED_VEGETARIAN = RecipeCategory.vegetarian;

    private static final Integer DEFAULT_SERVINGS = 1;
    private static final Integer UPDATED_SERVINGS = 2;

    private static final String DEFAULT_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_INGREDIENT = "CCCCCCCCCC";
    private static final String UPDATED_INGREDIENT = "DDDDDDDDDD";

    private static final String ENTITY_API_URL = "/api/recipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt());

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeMockMvc;

    private Recipe recipe;

    private Set<Ingredient> IngredientsSet = new HashSet<>();

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createEntity(EntityManager em, Set<Ingredient> IngredientsSet) {
        return new Recipe().category(DEFAULT_VEGETARIAN).servings(DEFAULT_SERVINGS).instructions(DEFAULT_INSTRUCTIONS).ingredients(IngredientsSet);
    }


    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingredient createIngredientEntity(EntityManager em) {
        return new Ingredient().name(DEFAULT_INGREDIENT);
    }

    @BeforeEach
    public void initTest() {
        Ingredient ingredient = createIngredientEntity(em);
        ingredientRepository.saveAndFlush(ingredient);
        IngredientsSet = Collections.singleton(ingredient);
        recipe = createEntity(em, IngredientsSet);
    }

    @Test
    @Transactional
    void createRecipe() throws Exception {
        int databaseSizeBeforeCreate = recipeRepository.findAll().size();
        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);
        restRecipeMockMvc
                .perform(post(ENTITY_API_URL)
                        .header(API_KEY_HEADER, APIKey)
                        .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
                .andExpect(status().isCreated());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate + 1);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getCategory()).isEqualTo(DEFAULT_VEGETARIAN);
        assertThat(testRecipe.getServings()).isEqualTo(DEFAULT_SERVINGS);
        assertThat(testRecipe.getInstructions()).isEqualTo(DEFAULT_INSTRUCTIONS);
        assertThat(testRecipe.getIngredients()).isEqualTo(IngredientsSet);
    }

    @Test
    @Transactional
    void createRecipeWithExistingId() throws Exception {
        // Create the Recipe with an existing ID
        recipe.setId(1L);
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        int databaseSizeBeforeCreate = recipeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeMockMvc
                .perform(post(ENTITY_API_URL)
                        .header(API_KEY_HEADER, APIKey)
                        .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getRecipesCriteria() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList
        getWithCriteriaFound("");

        // Id Filter
        getWithCriteriaFound("id.eq=" + recipe.getId().intValue());
        getWithCriteriaNotFound("id.eq=" + 9999L);

        // Category Filter TODO fix Enum filter
        getWithCriteriaFound("category.eq=" + DEFAULT_VEGETARIAN);
        getWithCriteriaNotFound("category.eq=" + UPDATED_VEGETARIAN);

        // Servings Filter
        getWithCriteriaFound("servings.eq=" + DEFAULT_SERVINGS);
        getWithCriteriaNotFound("servings.eq=" + UPDATED_SERVINGS);

        // Ingredients Filter
        getWithCriteriaFound("ingredients.in=" + DEFAULT_INGREDIENT);
        getWithCriteriaNotFound("ingredients.nin=" + DEFAULT_INGREDIENT);

        // Instructions Filter
        getWithCriteriaFound("instructions.contains=" + DEFAULT_INSTRUCTIONS);
        getWithCriteriaNotFound("instructions.notContains=" + UPDATED_INSTRUCTIONS);

        // Combined Filter
        getWithCriteriaFound("servings.eq=" + DEFAULT_SERVINGS + "&ingredients.in=" + DEFAULT_INGREDIENT);
        getWithCriteriaNotFound("servings.eq=" + UPDATED_SERVINGS + "&ingredients.in=" + DEFAULT_INGREDIENT);

        // Combined Filter
        getWithCriteriaFound("instructions.contains=" + DEFAULT_INSTRUCTIONS + "&ingredients.nin=" + UPDATED_INGREDIENT);
        getWithCriteriaNotFound("instructions.contains=" + DEFAULT_INSTRUCTIONS + "&ingredients.in=" + UPDATED_INGREDIENT);

    }

    private void getWithCriteriaFound(String filter) throws Exception {
        restRecipeMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter).header(API_KEY_HEADER, APIKey))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(recipe.getId().intValue())))
                .andExpect(jsonPath("$.[*].category").value(recipe.getCategory().toString()))
                .andExpect(jsonPath("$.[*].servings").value(hasItem(DEFAULT_SERVINGS)))
                .andExpect(jsonPath("$.[*].instructions").value(hasItem(DEFAULT_INSTRUCTIONS)))
                .andExpect(jsonPath("$.[*].ingredients[0].id").value(IngredientsSet.stream().toList().get(0).getId().intValue()))
                .andExpect(jsonPath("$.[*].ingredients[0].name").value(IngredientsSet.stream().toList().get(0).getName()));
    }

    private void getWithCriteriaNotFound(String filter) throws Exception {
        restRecipeMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter).header(API_KEY_HEADER, APIKey))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    void getRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get the recipe
        restRecipeMockMvc
                .perform(get(ENTITY_API_URL_ID, recipe.getId()).header(API_KEY_HEADER, APIKey))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(recipe.getId().intValue()))
                .andExpect(jsonPath("$.category").value(recipe.getCategory().toString()))
                .andExpect(jsonPath("$.servings").value(DEFAULT_SERVINGS))
                .andExpect(jsonPath("$.instructions").value(DEFAULT_INSTRUCTIONS))
                .andExpect(jsonPath("$.ingredients").isArray());
    }

    @Test
    @Transactional
    void getNonExistingRecipe() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE).header(API_KEY_HEADER, APIKey)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void getRecipeWithoutApiKey() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void getRecipeWithInvalidApiKey() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE).header(API_KEY_HEADER, "wrong-api")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void putExistingRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Update the recipe
        Recipe updatedRecipe = recipeRepository.findById(recipe.getId()).get();
        Ingredient updatedIngredient = new Ingredient().name(UPDATED_INGREDIENT);
        ingredientRepository.saveAndFlush(updatedIngredient);
        // Disconnect from session so that the updates on updatedRecipe are not directly saved in db
        em.detach(updatedRecipe);
        Set<Ingredient> updatedIngredients = Collections.singleton(updatedIngredient);
        updatedRecipe.category(UPDATED_VEGETARIAN).servings(UPDATED_SERVINGS).instructions(UPDATED_INSTRUCTIONS).ingredients(updatedIngredients);
        RecipeDTO recipeDTO = recipeMapper.toDto(updatedRecipe);

        restRecipeMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, recipeDTO.id())
                                .header(API_KEY_HEADER, APIKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(recipeDTO))
                )
                .andExpect(status().isOk());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getCategory()).isEqualTo(UPDATED_VEGETARIAN);
        assertThat(testRecipe.getServings()).isEqualTo(UPDATED_SERVINGS);
        assertThat(testRecipe.getInstructions()).isEqualTo(UPDATED_INSTRUCTIONS);
        assertThat(testRecipe.getIngredients()).isEqualTo(updatedIngredients);
    }

    @Test
    @Transactional
    void putNonExistingRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, recipeDTO.id())
                                .header(API_KEY_HEADER, APIKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(recipeDTO))
                )
                .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, count.incrementAndGet())
                                .header(API_KEY_HEADER, APIKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(recipeDTO))
                )
                .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();
        recipe.setId(count.incrementAndGet());

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeMockMvc
                .perform(put(ENTITY_API_URL)
                        .header(API_KEY_HEADER, APIKey)
                        .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
                .andExpect(status().isMethodNotAllowed());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeDelete = recipeRepository.findAll().size();

        // Delete the recipe
        restRecipeMockMvc
                .perform(delete(ENTITY_API_URL_ID, recipe.getId()).header(API_KEY_HEADER, APIKey).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

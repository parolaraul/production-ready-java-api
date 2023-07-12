package com.parolaraul.recipeapi.service.impl;

import com.parolaraul.recipeapi.domain.Recipe;
import com.parolaraul.recipeapi.repository.RecipeRepository;
import com.parolaraul.recipeapi.service.dto.RecipeDTO;
import com.parolaraul.recipeapi.service.mapper.RecipeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        RecipeDTO recipeDTO = new RecipeDTO(null, null, null, null, null);
        Recipe recipe = new Recipe();
        when(recipeMapper.toEntity(recipeDTO)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeMapper.toDto(recipe)).thenReturn(recipeDTO);

        RecipeDTO result = recipeService.save(recipeDTO);

        assertEquals(recipeDTO, result);
        verify(recipeRepository, times(1)).save(recipe);
        verify(recipeMapper, times(1)).toDto(recipe);
    }

    @Test
    public void testUpdate() {
        RecipeDTO recipeDTO = new RecipeDTO(null, null, null, null, null);
        Recipe recipe = new Recipe();
        when(recipeMapper.toEntity(recipeDTO)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeMapper.toDto(recipe)).thenReturn(recipeDTO);

        RecipeDTO result = recipeService.update(recipeDTO);

        assertEquals(recipeDTO, result);
        verify(recipeRepository, times(1)).save(recipe);
        verify(recipeMapper, times(1)).toDto(recipe);
    }

    @Test
    public void testFindAll() {
        Pageable pageable = Pageable.unpaged();
        Recipe recipe = new Recipe();
        RecipeDTO recipeDTO = new RecipeDTO(null, null, null, null, null);
        Page<Recipe> recipePage = new PageImpl<>(Collections.singletonList(recipe));
        when(recipeRepository.findAll(pageable)).thenReturn(recipePage);
        when(recipeRepository.findAllWithIngredientsByIds(Collections.singletonList(recipe.getId()))).thenReturn(Collections.singletonList(recipe));
        when(recipeMapper.toDto(recipe)).thenReturn(recipeDTO);

        Page<RecipeDTO> result = recipeService.findAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(recipeDTO, result.getContent().get(0));
        verify(recipeRepository, times(1)).findAll(pageable);
        verify(recipeRepository, times(1)).findAllWithIngredientsByIds(Collections.singletonList(recipe.getId()));
        verify(recipeMapper, times(1)).toDto(recipe);
    }

    @Test
    public void testFindOne() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        RecipeDTO recipeDTO = new RecipeDTO(null, null, null, null, null);
        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
        when(recipeMapper.toDto(recipe)).thenReturn(recipeDTO);

        Optional<RecipeDTO> result = recipeService.findOne(id);

        assertEquals(recipeDTO, result.orElse(null));
        verify(recipeRepository, times(1)).findById(id);
        verify(recipeMapper, times(1)).toDto(recipe);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        recipeService.delete(id);
        verify(recipeRepository, times(1)).deleteById(id);
    }
}

package com.parolaraul.recipeapi.service.impl;

import com.parolaraul.recipeapi.domain.Ingredient;
import com.parolaraul.recipeapi.repository.IngredientRepository;
import com.parolaraul.recipeapi.service.dto.IngredientDTO;
import com.parolaraul.recipeapi.service.mapper.IngredientMapper;
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

public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, null);
        Ingredient ingredient = new Ingredient();
        when(ingredientMapper.toEntity(ingredientDTO)).thenReturn(ingredient);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapper.toDto(ingredient)).thenReturn(ingredientDTO);

        IngredientDTO result = ingredientService.save(ingredientDTO);

        assertEquals(ingredientDTO, result);
    }

    @Test
    public void testUpdate() {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, null);
        Ingredient ingredient = new Ingredient();
        when(ingredientMapper.toEntity(ingredientDTO)).thenReturn(ingredient);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapper.toDto(ingredient)).thenReturn(ingredientDTO);

        IngredientDTO result = ingredientService.update(ingredientDTO);

        assertEquals(ingredientDTO, result);
    }

    @Test
    public void testFindAll() {
        Pageable pageable = Pageable.unpaged();
        Ingredient ingredient = new Ingredient();
        IngredientDTO ingredientDTO = new IngredientDTO(1L, null);
        Page<Ingredient> ingredientPage = new PageImpl<>(Collections.singletonList(ingredient));
        when(ingredientRepository.findAll(pageable)).thenReturn(ingredientPage);
        when(ingredientMapper.toDto(ingredient)).thenReturn(ingredientDTO);

        Page<IngredientDTO> result = ingredientService.findAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(ingredientDTO, result.getContent().get(0));
    }

    @Test
    public void testFindOne() {
        Long id = 1L;
        Ingredient ingredient = new Ingredient();
        IngredientDTO ingredientDTO = new IngredientDTO(1L, null);
        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));
        when(ingredientMapper.toDto(ingredient)).thenReturn(ingredientDTO);

        Optional<IngredientDTO> result = ingredientService.findOne(id);

        assertEquals(ingredientDTO, result.orElse(null));
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        ingredientService.delete(id);
        verify(ingredientRepository, times(1)).deleteById(id);
    }
}

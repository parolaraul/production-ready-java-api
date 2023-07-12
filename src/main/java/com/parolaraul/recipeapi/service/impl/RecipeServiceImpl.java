package com.parolaraul.recipeapi.service.impl;

import com.parolaraul.recipeapi.domain.Recipe;
import com.parolaraul.recipeapi.repository.RecipeRepository;
import com.parolaraul.recipeapi.service.RecipeService;
import com.parolaraul.recipeapi.service.dto.RecipeDTO;
import com.parolaraul.recipeapi.service.mapper.RecipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Recipe}.
 */
@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public RecipeDTO save(RecipeDTO recipeDTO) {
        log.debug("Request to save Recipe : {}", recipeDTO);
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    @Override
    public RecipeDTO update(RecipeDTO recipeDTO) {
        log.debug("Request to update Recipe : {}", recipeDTO);
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recipes");
        return getPaginatedRecipes(pageable).map(recipeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecipeDTO> findOne(Long id) {
        log.debug("Request to get Recipe : {}", id);
        return recipeRepository.findById(id).map(recipeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recipe : {}", id);
        recipeRepository.deleteById(id);
    }

    private Page<Recipe> getPaginatedRecipes(Pageable pageable) {
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        List<Long> recipeIds = recipePage.getContent().stream().map(Recipe::getId).collect(Collectors.toList());
        List<Recipe> recipesWithIngredients = recipeRepository.findAllWithIngredientsByIds(recipeIds);

        return new PageImpl<>(recipesWithIngredients, pageable, recipePage.getTotalElements());
    }
}

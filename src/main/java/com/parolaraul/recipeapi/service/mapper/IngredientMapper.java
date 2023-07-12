package com.parolaraul.recipeapi.service.mapper;

import com.parolaraul.recipeapi.domain.Ingredient;
import com.parolaraul.recipeapi.service.dto.IngredientDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Ingredient} and its DTO {@link IngredientDTO}.
 */
@Mapper(componentModel = "spring")
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {
}

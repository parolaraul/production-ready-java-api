package com.parolaraul.recipeapi.service;

import com.parolaraul.recipeapi.service.criteria.RecipeCriteria;
import com.parolaraul.recipeapi.service.dto.RecipeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Interface for managing {@link com.parolaraul.recipeapi.domain.Recipe} with {@link com.parolaraul.recipeapi.service.criteria.Criteria}.
 */
public interface RecipeQBService {

    /**
     * Find recipes by criteria, all if criteria is null
     *
     * @param criteria the criteria to query
     * @param pageable pageable query params
     * @return the entity paginated.
     */
    @Transactional(readOnly = true)
    Page<RecipeDTO> findByCriteria(RecipeCriteria criteria, Pageable pageable);
}

package com.parolaraul.recipeapi.rest;

import com.parolaraul.recipeapi.repository.RecipeRepository;
import com.parolaraul.recipeapi.rest.exceptions.BadRequestException;
import com.parolaraul.recipeapi.rest.util.PaginationUtil;
import com.parolaraul.recipeapi.rest.util.ResponseUtil;
import com.parolaraul.recipeapi.service.RecipeService;
import com.parolaraul.recipeapi.service.dto.RecipeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.parolaraul.recipeapi.domain.Recipe}.
 */
@RestController
@RequestMapping("/api")
public class RecipeResource {

    private final Logger log = LoggerFactory.getLogger(RecipeResource.class);

    private static final String ENTITY_NAME = "Recipe";

    private final RecipeService recipeService;

    private final RecipeRepository recipeRepository;

    public RecipeResource(RecipeService recipeService, RecipeRepository recipeRepository) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    /**
     * {@code POST  /recipes} : Create a new recipe.
     *
     * @param recipeDTO the recipeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeDTO, or with status {@code 400 (Bad Request)} if the recipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipes")
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) throws URISyntaxException {
        log.debug("REST request to save Recipe : {}", recipeDTO);
        if (recipeDTO.id() != null) {
            throw new BadRequestException("A new recipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeDTO result = recipeService.save(recipeDTO);
        return ResponseEntity
                .created(new URI("/api/recipes/" + result.id()))
                .body(result);
    }

    /**
     * {@code PUT  /recipes/:id} : Updates an existing recipe.
     *
     * @param id        the id of the recipeDTO to save.
     * @param recipeDTO the recipeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeDTO,
     * or with status {@code 400 (Bad Request)} if the recipeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipes/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody RecipeDTO recipeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Recipe : {}, {}", id, recipeDTO);
        if (recipeDTO.id() == null) {
            throw new BadRequestException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeDTO.id())) {
            throw new BadRequestException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeRepository.existsById(id)) {
            throw new BadRequestException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecipeDTO result = recipeService.update(recipeDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * {@code GET  /recipes} : get all the recipes.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipes in body.
     */
    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Recipes");
        Page<RecipeDTO> page = recipeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recipes/:id} : get the "id" recipe.
     *
     * @param id the id of the recipeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id) {
        log.debug("REST request to get Recipe : {}", id);
        Optional<RecipeDTO> recipeDTO = recipeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeDTO, ENTITY_NAME, id);
    }

    /**
     * {@code DELETE  /recipes/:id} : delete the "id" recipe.
     *
     * @param id the id of the recipeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        log.debug("REST request to delete Recipe : {}", id);
        recipeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}

package com.parolaraul.recipeapi.rest;

import com.parolaraul.recipeapi.repository.IngredientRepository;
import com.parolaraul.recipeapi.rest.exceptions.BadRequestException;
import com.parolaraul.recipeapi.rest.util.PaginationUtil;
import com.parolaraul.recipeapi.rest.util.ResponseUtil;
import com.parolaraul.recipeapi.service.IngredientService;
import com.parolaraul.recipeapi.service.dto.IngredientDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.parolaraul.recipeapi.domain.Ingredient}.
 */
@RestController
@RequestMapping("/api")
public class IngredientResource {

    private final Logger log = LoggerFactory.getLogger(IngredientResource.class);

    private static final String ENTITY_NAME = "Ingredient";

    private final IngredientService ingredientService;

    private final IngredientRepository ingredientRepository;

    public IngredientResource(IngredientService ingredientService, IngredientRepository ingredientRepository) {
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * {@code POST  /ingredients} : Create a new ingredient.
     *
     * @param ingredientDTO the ingredientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingredientDTO, or with status {@code 400 (Bad Request)} if the ingredient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ingredients")
    public ResponseEntity<IngredientDTO> createIngredient(@Valid @RequestBody IngredientDTO ingredientDTO) throws URISyntaxException {
        log.debug("REST request to save Ingredient : {}", ingredientDTO);
        if (ingredientDTO.id() != null) {
            throw new BadRequestException("A new ingredient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientDTO result = ingredientService.save(ingredientDTO);
        return ResponseEntity
                .created(new URI("/api/ingredients/" + result.id()))
                .body(result);
    }

    /**
     * {@code PUT  /ingredients/:id} : Updates an existing ingredient.
     *
     * @param id            the id of the ingredientDTO to save.
     * @param ingredientDTO the ingredientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientDTO,
     * or with status {@code 400 (Bad Request)} if the ingredientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingredientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody IngredientDTO ingredientDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Ingredient : {}, {}", id, ingredientDTO);
        if (ingredientDTO.id() == null) {
            throw new BadRequestException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingredientDTO.id())) {
            throw new BadRequestException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingredientRepository.existsById(id)) {
            throw new BadRequestException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IngredientDTO result = ingredientService.update(ingredientDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * {@code GET  /ingredients} : get all the ingredients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredients in body.
     */
    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDTO>> getAllIngredients(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ingredients");
        Page<IngredientDTO> page = ingredientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredients/:id} : get the "id" ingredient.
     *
     * @param id the id of the ingredientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingredientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDTO> getIngredient(@PathVariable Long id) {
        log.debug("REST request to get Ingredient : {}", id);
        Optional<IngredientDTO> ingredientDTO = ingredientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredientDTO, ENTITY_NAME, id);
    }

    /**
     * {@code DELETE  /ingredients/:id} : delete the "id" ingredient.
     *
     * @param id the id of the ingredientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ingredients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        log.debug("REST request to delete Ingredient : {}", id);
        ingredientService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}

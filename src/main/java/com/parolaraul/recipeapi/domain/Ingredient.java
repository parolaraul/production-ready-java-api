package com.parolaraul.recipeapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnoreProperties(value = {"ingredients"}, allowSetters = true)
    private Set<Recipe> recipes = new HashSet<>();


    public Long getId() {
        return this.id;
    }

    public Ingredient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Ingredient name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        if (this.recipes != null) {
            this.recipes.forEach(i -> i.removeIngredient(this));
        }
        if (recipes != null) {
            recipes.forEach(i -> i.addIngredient(this));
        }
        this.recipes = recipes;
    }

    public Ingredient recipes(Set<Recipe> recipes) {
        this.setRecipes(recipes);
        return this;
    }

    public Ingredient addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
        recipe.getIngredients().add(this);
        return this;
    }

    public Ingredient removeRecipe(Recipe recipe) {
        this.recipes.remove(recipe);
        recipe.getIngredients().remove(this);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingredient)) {
            return false;
        }
        return id != null && id.equals(((Ingredient) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                "}";
    }
}

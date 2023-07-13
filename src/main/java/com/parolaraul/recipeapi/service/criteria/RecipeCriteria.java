package com.parolaraul.recipeapi.service.criteria;

import com.parolaraul.recipeapi.domain.RecipeCategory;

import java.util.Objects;
import java.util.Set;

public class RecipeCriteria implements Criteria {

    Filter<Long> id;
    Filter<RecipeCategory> category;
    Filter<Integer> servings;
    Filter<Set<String>> ingredients;
    Filter<String> instructions;

    public RecipeCriteria() {
    }

    public RecipeCriteria(RecipeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.servings = other.servings == null ? null : other.servings.copy();
        this.ingredients = other.ingredients == null ? null : other.ingredients.copy();
        this.instructions = other.instructions == null ? null : other.instructions.copy();
    }

    @Override
    public RecipeCriteria copy() {
        return new RecipeCriteria(this);
    }

    public Filter<Long> getId() {
        return id;
    }

    public void setId(Filter<Long> id) {
        this.id = id;
    }

    public Filter<RecipeCategory> getCategory() {
        return category;
    }

    public void setCategory(Filter<RecipeCategory> category) {
        this.category = category;
    }

    public Filter<Integer> getServings() {
        return servings;
    }

    public void setServings(Filter<Integer> servings) {
        this.servings = servings;
    }

    public Filter<Set<String>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Filter<Set<String>> ingredients) {
        this.ingredients = ingredients;
    }

    public Filter<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(Filter<String> instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeCriteria)) return false;
        RecipeCriteria that = (RecipeCriteria) o;
        return getId().equals(that.getId()) && getCategory().equals(that.getCategory()) && getServings().equals(that.getServings()) && Objects.equals(getIngredients(), that.getIngredients()) && Objects.equals(getInstructions(), that.getInstructions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategory(), getServings(), getIngredients(), getInstructions());
    }

    @Override
    public String toString() {
        return "RecipeCriteria{" +
                "id=" + id +
                ", category=" + category +
                ", servings=" + servings +
                ", ingredients=" + ingredients +
                ", instructions=" + instructions +
                '}';
    }
}

package com.parolaraul.recipeapi.service.criteria;

import com.parolaraul.recipeapi.domain.RecipeCategory;

import java.util.Objects;

public class RecipeCriteria implements Criteria {

    /**
     * Class for filtering RecipeCategory enum
     */
    public static class RecipeCategoryFilter extends Filter<RecipeCategory> {

        public RecipeCategoryFilter() {}

        public RecipeCategoryFilter(RecipeCategoryFilter filter) {
            super(filter);
        }

        @Override
        public RecipeCategoryFilter copy() {
            return new RecipeCategoryFilter(this);
        }
    }

    Filter<Long> id;
    RecipeCategoryFilter category;
    Filter<Integer> servings;
    Filter<String> ingredients;
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

    public Filter<Long> getId() {
        return id;
    }

    public void setId(Filter<Long> id) {
        this.id = id;
    }

    public RecipeCategoryFilter getCategory() {
        return category;
    }

    public void setCategory(RecipeCategoryFilter category) {
        this.category = category;
    }

    public Filter<Integer> getServings() {
        return servings;
    }

    public void setServings(Filter<Integer> servings) {
        this.servings = servings;
    }

    public Filter<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Filter<String> ingredients) {
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

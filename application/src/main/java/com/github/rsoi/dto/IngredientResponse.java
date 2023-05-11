package com.github.rsoi.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.rsoi.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class IngredientResponse {
    String name;
    long ingredientId;
    int quantity;

    public static IngredientResponse from(Ingredient ingredient) {
        return new IngredientResponse(ingredient.getName(), ingredient.getId(), ingredient.getQuantity());
    }

    public static Collection<IngredientResponse> list(Collection<Ingredient> ingredients) {
        return ingredients.stream()
            .map(IngredientResponse::from)
            .toList();
    }
}

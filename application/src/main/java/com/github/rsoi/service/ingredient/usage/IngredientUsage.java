package com.github.rsoi.service.ingredient.usage;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.github.rsoi.domain.Ingredient;
import com.github.rsoi.domain.SupplyIngredient;
import lombok.Value;

@Value
public class IngredientUsage {
    Map<Long, Ingredient> ingredientById;
    Map<Long, Double> dailyUsageByIngredientId;

    public Set<Long> getIngredientsIds() {
        return dailyUsageByIngredientId.keySet();
    }

    private double getDailyUsageByIngredientId(long ingredientId) {
        return dailyUsageByIngredientId.getOrDefault(ingredientId, 0.0);
    }

    public Optional<SupplyIngredient> getSupplyIngredientIfNeeded(long ingredientId) {
        var dailyUsage = getDailyUsageByIngredientId(ingredientId);
        if (dailyUsage < 1) {
            return Optional.empty();
        }

        var ingredient = ingredientById.get(ingredientId);

        var supplyIngredient = new SupplyIngredient();
        supplyIngredient.setIngredient(ingredient);
        supplyIngredient.setQuantity(Math.toIntExact(Math.round(dailyUsage * 7)));

        return Optional.of(supplyIngredient);
    }
}

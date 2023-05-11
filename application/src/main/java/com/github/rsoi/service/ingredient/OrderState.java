package com.github.rsoi.service.ingredient;

import java.util.Collection;
import java.util.Map;

import com.github.rsoi.domain.Ingredient;
import com.github.rsoi.domain.OrderIngredient;
import lombok.Value;

@Value
public class OrderState {

    Collection<Ingredient> ingredients;
    Collection<Ingredient> notEnoughIngredients;
    Map<Long, Integer> ingredientUsageById;

    public boolean isSupplyRequired() {
        return !notEnoughIngredients.isEmpty();
    }

    public int getUsage(long id) {
        return ingredientUsageById.getOrDefault(id, 0);
    }

    public Collection<OrderIngredient> getOrderIngredients() {
        return ingredients.stream()
            .map(i -> new OrderIngredient(null, i, getUsage(i.getId())))
            .toList();
    }
}

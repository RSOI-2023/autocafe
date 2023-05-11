package com.github.rsoi.dto;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.rsoi.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class OrderResponse {
    OrderStatus orderStatus;
    Collection<IngredientResponse> notEnoughIngredients;

    public static OrderResponse completed() {
        return new OrderResponse(OrderStatus.COMPLETED, List.of());
    }

    public static OrderResponse notEnoughIngredients(Collection<Ingredient> ingredients) {
        return new OrderResponse(OrderStatus.NOT_ENOUGH_INGREDIENTS, IngredientResponse.list(ingredients));
    }
}

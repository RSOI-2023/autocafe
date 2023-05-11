package com.github.rsoi.service.ingredient;

import java.util.Collection;

import com.github.rsoi.dto.IngredientRequest;

public interface IngredientService {
    OrderState evaluateStocks(Collection<IngredientRequest> ingredientRequests);

    void applyUsage(OrderState orderState);
}

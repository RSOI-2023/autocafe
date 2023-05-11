package com.github.rsoi.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.rsoi.domain.Supply;
import com.github.rsoi.domain.SupplyIngredient;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class SupplyResponse {
    long supplyId;
    Collection<IngredientResponse> supplyIngredients;
    boolean isApplied;

    public static SupplyResponse from(Supply supply) {
        var ingredients = supply.getIngredients().stream().map(SupplyIngredient::getIngredient).toList();
        return new SupplyResponse(
            supply.getId(),
            IngredientResponse.list(ingredients),
            supply.getCompletedAt() != null
        );
    }
}

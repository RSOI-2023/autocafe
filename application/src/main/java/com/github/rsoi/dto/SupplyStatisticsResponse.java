package com.github.rsoi.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class SupplyStatisticsResponse {
    Collection<IngredientResponse> supplyIngredients;
}

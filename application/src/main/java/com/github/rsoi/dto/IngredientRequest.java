package com.github.rsoi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class IngredientRequest {
    long id;
    @Positive
    int quantity;
}

package com.github.rsoi.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class OrderRequest {
    @NotEmpty
    @Valid
    Collection<IngredientRequest> ingredients;
}

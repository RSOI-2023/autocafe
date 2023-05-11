package com.github.rsoi.repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.rsoi.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    default Map<Long, Ingredient> getIngredientMap() {
        return findAll().stream()
            .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
    }
}

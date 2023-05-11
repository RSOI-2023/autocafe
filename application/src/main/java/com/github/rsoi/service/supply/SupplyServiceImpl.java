package com.github.rsoi.service.supply;

import java.time.Instant;
import java.util.stream.Collectors;

import com.github.rsoi.domain.Ingredient;
import com.github.rsoi.domain.Supply;
import com.github.rsoi.domain.SupplyIngredient;
import com.github.rsoi.dto.SupplyResponse;
import com.github.rsoi.repository.SupplyRepository;
import com.github.rsoi.service.ingredient.usage.IngredientUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplyServiceImpl implements SupplyService {

    private static final int MIN_QUANTITY_TO_APPLY_SUPPLY = 100;

    private final SupplyRepository supplyRepository;
    private final IngredientUsageService ingredientUsageService;

    @Transactional
    @Override
    public SupplyResponse processSupply() {
        var supply = supplyRepository.findEmergingSupply().orElse(new Supply());

        var supplyIngredients = supply.getIngredients();

        var supplyIngredientIds = supplyIngredients.stream()
            .map(SupplyIngredient::getIngredient)
            .map(Ingredient::getId)
            .collect(Collectors.toSet());

        var ingredientUsage = ingredientUsageService.getIngredientUsage();
        for (var ingredientId : ingredientUsage.getIngredientsIds()) {
            //skipping already added ingredients
            if (supplyIngredientIds.contains(ingredientId)) {
                continue;
            }

            ingredientUsage.getSupplyIngredientIfNeeded(ingredientId).ifPresent(supplyIngredients::add);
        }

        supplyRepository.save(supply);

        var quantitySum = supplyIngredients.stream()
            .mapToInt(SupplyIngredient::getQuantity)
            .sum();

        if (quantitySum > MIN_QUANTITY_TO_APPLY_SUPPLY) {
            supply.setCompletedAt(Instant.now());
        }

        return SupplyResponse.from(supply);
    }
}

package com.github.rsoi.service.statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.rsoi.domain.SupplyIngredient;
import com.github.rsoi.dto.IngredientResponse;
import com.github.rsoi.dto.SupplyStatisticsResponse;
import com.github.rsoi.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticServiceImpl implements StatisticService {

    private static final int DAYS_TO_COLLECT_SUPPLY_STATISTIC = 20;

    private final SupplyRepository supplyRepository;

    @Override
    public SupplyStatisticsResponse getSupplyStatistics() {
        var after = Instant.now().minus(DAYS_TO_COLLECT_SUPPLY_STATISTIC, ChronoUnit.DAYS);

        var supplies = supplyRepository.findSuppliesByCompletedAtAfter(after);

        var sumQuantityByIngredient = supplies.stream()
            .flatMap(s -> s.getIngredients().stream())
            .collect(Collectors.toMap(Function.identity(), SupplyIngredient::getQuantity, Integer::sum));

        var ingredientResponses = sumQuantityByIngredient.entrySet().stream()
            .map(e -> {
                var ingredient = e.getKey().getIngredient();
                return new IngredientResponse(
                    ingredient.getName(),
                    ingredient.getId(),
                    e.getValue()
                );
            })
            .sorted(Comparator.comparingInt(IngredientResponse::getQuantity))
            .toList();

        return new SupplyStatisticsResponse(ingredientResponses);
    }
}

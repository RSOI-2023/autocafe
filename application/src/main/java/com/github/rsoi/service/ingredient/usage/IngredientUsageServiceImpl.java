package com.github.rsoi.service.ingredient.usage;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.rsoi.domain.OrderIngredient;
import com.github.rsoi.repository.IngredientRepository;
import com.github.rsoi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IngredientUsageServiceImpl implements IngredientUsageService {

    private static final int DAYS_TO_COLLECT_USAGE = 10;

    private final OrderRepository orderRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public IngredientUsage getIngredientUsage() {
        var after = Instant.now().minus(DAYS_TO_COLLECT_USAGE, DAYS);
        var recentOrders = orderRepository.findOrdersByCompletedAtAfter(after);
        var weekUsageByIngredientId = recentOrders.stream()
            .flatMap(o -> o.getIngredients().stream())
            .collect(Collectors.toMap(
                o -> o.getIngredient().getId(),
                OrderIngredient::getQuantity,
                Integer::sum
            ));
        var ingredientDailyUsages = weekUsageByIngredientId.entrySet().stream()
            //skipping zero usage
            .filter(e -> e.getValue() > 0)
            .map(e -> Map.entry(e.getKey(), e.getValue() * 1.0 / DAYS_TO_COLLECT_USAGE))
            .toList();
        var dailyUsageByIngredientId = ingredientDailyUsages.stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        var ingredientById = ingredientRepository.getIngredientMap();
        return new IngredientUsage(ingredientById, dailyUsageByIngredientId);
    }
}

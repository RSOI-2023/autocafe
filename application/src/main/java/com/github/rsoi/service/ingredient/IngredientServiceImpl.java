package com.github.rsoi.service.ingredient;

import java.util.Collection;
import java.util.stream.Collectors;

import com.github.rsoi.dto.IngredientRequest;
import com.github.rsoi.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public OrderState evaluateStocks(Collection<IngredientRequest> ingredientRequests) {
        var ingredientUsageById = ingredientRequests.stream()
            .collect(Collectors.toMap(IngredientRequest::getId, IngredientRequest::getQuantity));

        var ingredients = ingredientRepository.findAllById(ingredientUsageById.keySet());
        //Some ingredients not found
        if (ingredients.size() != ingredientUsageById.keySet().size()) {
            throw new EntityNotFoundException();
        }

        var notEnoughIngredients = ingredients.stream()
            .filter(i -> {
                var usage = ingredientUsageById.get(i.getId());
                //not enough ingredients
                return usage > i.getQuantity();
            })
            .toList();

        return new OrderState(
            ingredients,
            notEnoughIngredients,
            ingredientUsageById
        );
    }

    @Override
    public void applyUsage(OrderState orderState) {
        var ingredients = orderState.getIngredients();
        ingredients.forEach(i -> {
            var quantity = i.getQuantity();
            quantity -= orderState.getUsage(i.getId());
            i.setQuantity(quantity);
        });
    }
}

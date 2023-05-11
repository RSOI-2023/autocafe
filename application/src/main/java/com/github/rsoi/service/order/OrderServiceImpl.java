package com.github.rsoi.service.order;

import com.github.rsoi.domain.Order;
import com.github.rsoi.dto.OrderRequest;
import com.github.rsoi.dto.OrderResponse;
import com.github.rsoi.repository.OrderRepository;
import com.github.rsoi.service.ingredient.IngredientService;
import com.github.rsoi.service.ingredient.OrderState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final IngredientService ingredientService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponse handleOrder(OrderRequest orderRequest) {
        var orderState = ingredientService.evaluateStocks(orderRequest.getIngredients());
        if (orderState.isSupplyRequired()) {
            return OrderResponse.notEnoughIngredients(orderState.getNotEnoughIngredients());
        }

        ingredientService.applyUsage(orderState);
        saveOrder(orderState);
        return OrderResponse.completed();
    }

    private void saveOrder(OrderState orderState) {
        var order = new Order();
        order.setIngredients(orderState.getOrderIngredients());
        orderRepository.save(order);
    }
}

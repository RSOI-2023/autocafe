package com.github.rsoi.service.order;

import com.github.rsoi.dto.OrderRequest;
import com.github.rsoi.dto.OrderResponse;

public interface OrderService {
    OrderResponse handleOrder(OrderRequest orderRequest);
}

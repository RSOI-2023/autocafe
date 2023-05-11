package com.github.rsoi.repository;

import java.time.Instant;
import java.util.Collection;

import com.github.rsoi.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Collection<Order> findOrdersByCompletedAtAfter(Instant completedAfter);
}

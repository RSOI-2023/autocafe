package com.github.rsoi.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import com.github.rsoi.domain.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Optional<Supply> findSupplyByCompletedAt(Instant completedAt);

    default Optional<Supply> findEmergingSupply() {
        return findSupplyByCompletedAt(null);
    }

    Collection<Supply> findSuppliesByCompletedAtAfter(Instant completedAfter);
}

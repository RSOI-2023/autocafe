package com.github.rsoi.domain;

import java.time.Instant;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Entity
@Data
@Validated
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //TODO: N+1 on statistic
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @NotEmpty
    private Collection<OrderIngredient> ingredients;
    @NotNull
    private Instant completedAt = Instant.now();
}

package com.github.rsoi.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Entity
@Data
@Validated
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @NotEmpty
    private Collection<SupplyIngredient> ingredients = new ArrayList<>();
    private Instant completedAt = null;
}

package com.github.rsoi.controller;

import com.github.rsoi.dto.SupplyResponse;
import com.github.rsoi.service.supply.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supply")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplyController {

    private final SupplyService supplyService;

    @GetMapping("/calculate")
    public SupplyResponse calculateSupply() {
        return supplyService.processSupply();
    }
}

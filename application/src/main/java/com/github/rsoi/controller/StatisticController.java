package com.github.rsoi.controller;

import com.github.rsoi.dto.SupplyStatisticsResponse;
import com.github.rsoi.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/supply")
    public SupplyStatisticsResponse getSupplyStatistics() {
        return statisticService.getSupplyStatistics();
    }
}

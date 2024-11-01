package com.example.demo.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/statistics", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
public class StatisticsController {

    //private final StatisticsService statisticsService;

    @GetMapping("/max")
    public Double getMaxDistance() {
        return 1.1;
    }

    @GetMapping("/min")
    public Double getMinDistance() {
        return 2.2;
    }

    @GetMapping("/average")
    public Double getAverageDistance() {
        return 3.3;
    }
}

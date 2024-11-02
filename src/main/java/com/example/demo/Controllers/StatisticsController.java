package com.example.demo.Controllers;

import com.example.demo.Entities.IpStatistics;
import com.example.demo.Service.IIpStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
public class StatisticsController {

    @Autowired
    private final IIpStatisticsService statisticsService;

    @GetMapping("/max")
    public ResponseEntity<List<IpStatistics>> getMaxDistance() {
        List<IpStatistics> result = statisticsService.findMaxDistance();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/min")
    public ResponseEntity<List<IpStatistics>> getMinDistance() {
        List<IpStatistics> result = statisticsService.findMinDistance();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    //promedio distancia
    @GetMapping("/average")
    public ResponseEntity<List<Double>> getAverageDistance() {
        List<Double> result = statisticsService.findAverageDistance();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

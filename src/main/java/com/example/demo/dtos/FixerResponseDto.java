package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
public class FixerResponseDto {
    private boolean success;
    private long timestamp;
    private String base;
    private LocalDate date;
    private Map<String, Double> rates;
}

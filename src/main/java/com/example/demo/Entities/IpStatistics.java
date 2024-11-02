package com.example.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
public class IpStatistics {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String countryCode;
    private Double distance;
}

package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CountryInfoDto {
    private String ip;
    private LocalDateTime localDateTime;
    private Set<String> timeZones;
    private String name;
    private String isoCode;
    private List<LanguageDto> languages;
    private String currencyCode;
    private String currencyName;
    private Double currencyExchangeRateWithUsDollar;
    private Double latitude;
    private Double longitude;
    private Double buenosAiresLatitude;
    private Double buenosAiresLongitude;
    private Double distanceToBuenosAires;
}

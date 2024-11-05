package com.example.demo.Service;

import com.example.demo.dtos.FixerResponseDto;
import org.springframework.stereotype.Component;


import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UtilsComponent {

    private static final double EARTH_RADIUS_KM = 6371;
    private static final String USD_CODE = "USD";
    public static final double BUENOS_AIRES_LAT = -34.61315;
    public static final double BUENOS_AIRES_LON = -58.37723;

    public Set<String> getCurrentTimesByCountry(String countryCode, String countryName) {

        var timeZone = ZoneId.getAvailableZoneIds().stream().filter(zoneId -> zoneId.startsWith(countryCode)).toList();

        if (timeZone.isEmpty()) {
            timeZone = ZoneId.getAvailableZoneIds()
                    .stream().filter(zoneId -> zoneId.toLowerCase()
                            .contains(countryName.toLowerCase())).toList();
            if (timeZone.isEmpty()) {
                return new HashSet<>();
            }
        }
        return timeZone.stream()
                .map(zoneId -> ZonedDateTime.ofInstant(Instant.now(),
                        ZoneId.of(zoneId)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")))
                .collect(Collectors.toSet());
    }

    public Currency getCurrencyByCountryCode(String countryCode) {
        Locale locale = new Locale("", countryCode);
        try {
            Currency currency = Currency.getInstance(locale);
            if (currency == null) {
                throw new IllegalArgumentException("No se encontró moneda para el código de país: " + countryCode);
            }
            return currency;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Código de país inválido o sin moneda definida: " + countryCode, e);
        }
    }

    public double getDollarExchangeRate(FixerResponseDto fixerResponseDto, String currencyCode) {
        if (fixerResponseDto.getRates() == null || fixerResponseDto.getRates().isEmpty() ||
                !fixerResponseDto.getRates().containsKey(USD_CODE) || !fixerResponseDto.getRates().containsKey(currencyCode)) {
            return 0;
        }

        var currencyToEur = fixerResponseDto.getRates().get(currencyCode);
        var eurToUsd = fixerResponseDto.getRates().get(USD_CODE);
        return currencyToEur / eurToUsd;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    public Double getDistanceFromBuenosAires(double latitude, double longitude) {
        return calculateDistance(BUENOS_AIRES_LAT, BUENOS_AIRES_LON, latitude, longitude);
    }

}

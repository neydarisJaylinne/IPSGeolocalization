package com.example.demo.ServicesTests;

import com.example.demo.Service.UtilsService;
import com.example.demo.dtos.FixerResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilsServiceTest {

    private final UtilsService utilsService = new UtilsService();

    @Test
    void testGetCurrentTimesByCountryWithValidCountryCode() {
        String countryCode = "US"; // Estados Unidos
        String countryName = "United States";

        Set<String> currentTimes = utilsService.getCurrentTimesByCountry(countryCode, countryName);

        assertFalse(currentTimes.isEmpty(), "La lista de horas no debería estar vacía para un código de país válido");

    }

    @Test
    public void testGetCurrentTimesByCountryWithInvalidCountryCodeButValidCountryName() {
        String countryCode = "ZZ";
        String countryName = "United States";

        Set<String> currentTimes = utilsService.getCurrentTimesByCountry(countryCode, countryName);

        assertFalse(currentTimes.isEmpty(), "La lista de horas no debería estar vacía para un nombre de país válido");
    }

    @Test
    public void testGetCurrentTimesByCountryWithInvalidCountryCodeAndName() {
        String countryCode = "ZZ";
        String countryName = "NoCountry";

        Set<String> currentTimes = utilsService.getCurrentTimesByCountry(countryCode, countryName);

        assertTrue(currentTimes.isEmpty(), "La lista de horas debería estar vacía para un código y nombre de país no válidos");
    }

    @Test
    public void testGetDollarExchangeRateWithEmptyRatesValue() {
        FixerResponseDto fixerResponseDto = new FixerResponseDto();
        String validCurrency = "USD";
        fixerResponseDto.setRates(Collections.emptyMap());
        double response = utilsService.getDollarExchangeRate(fixerResponseDto, validCurrency);
        assertEquals(response, 0);
        }

    @Test
    public void testGetDollarExchangeRateWithNullRatesValue() {
        FixerResponseDto fixerResponseDto = new FixerResponseDto();
        String validCurrency = "USD";
        fixerResponseDto.setRates(null);
        double response = utilsService.getDollarExchangeRate(fixerResponseDto, validCurrency);
        assertEquals(response, 0);
    }

    @Test
    public void testGetDollarExchangeRateWithEmptyCurrencyCodeValue() {
        FixerResponseDto fixerResponseDto = new FixerResponseDto();
        String validCurrency = "";
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD_CODE", 1.1);
        fixerResponseDto.setRates(rates);
        double response = utilsService.getDollarExchangeRate(fixerResponseDto, validCurrency);
        assertEquals(response, 0);
    }
    @Test
    public void testGetDollarExchangeRateWithInvalidKey() {
        FixerResponseDto fixerResponseDto = new FixerResponseDto();
        String validCurrency = "USD";
        Map<String, Double> rates = new HashMap<>();
        rates.put("TEST", 1.1);
        fixerResponseDto.setRates(rates);
        double response = utilsService.getDollarExchangeRate(fixerResponseDto, validCurrency);
        assertEquals(response, 0);
    }
}

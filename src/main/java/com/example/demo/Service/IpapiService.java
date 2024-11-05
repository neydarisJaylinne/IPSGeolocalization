package com.example.demo.Service;

import com.example.demo.Entities.IpStatistics;
import com.example.demo.Repository.IIPStatisticsRepository;
import com.example.demo.clients.FixerClient;
import com.example.demo.clients.IpapiClient;
import com.example.demo.dtos.CountryInfoDto;
import com.example.demo.dtos.IpapiResponseDto;
import com.example.demo.dtos.LanguageDto;
import jakarta.annotation.Resource;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static com.example.demo.Service.UtilsComponent.BUENOS_AIRES_LAT;
import static com.example.demo.Service.UtilsComponent.BUENOS_AIRES_LON;


/// logica de negocio, todos los metodos que operan con otros servicios
@Service
public class IpapiService {

    @Resource
    private final IpapiClient ipapiClient;
    private final FixerClient fixerClient;
    private final UtilsComponent utilsComponent;
    @Autowired
    private IIPStatisticsRepository statisticsRepository;


    public IpapiService(IpapiClient ipapiClient, FixerClient fixerClient, UtilsComponent utilsComponent) {
        this.ipapiClient = ipapiClient;
        this.fixerClient = fixerClient;
        this.utilsComponent = utilsComponent;
    }

    public CountryInfoDto getCountryData(String ip) {
        IpapiResponseDto ipapiResponseDto = new IpapiResponseDto();
        ipapiResponseDto =  ipapiClient.getCountryData(ip);
        if (ipapiResponseDto == null) {
            throw new ServiceException("No se pudo obtener la información de la API para la IP proporcionada.");
        }
        if (ipapiResponseDto.getCountry_code() == null || ipapiResponseDto.getCountry_name() == null) {
            throw new NullPointerException("La respuesta de la API no contiene los datos necesarios.");
        }
        CountryInfoDto countryInfoDto = buildCountryInfoDto(ipapiResponseDto);
        if (countryInfoDto.getDistanceToBuenosAires() < 0) {
            throw new IllegalArgumentException("La distancia a Buenos Aires es inválida.");
        }
        saveInvocation(ipapiResponseDto, countryInfoDto.getDistanceToBuenosAires());
        return countryInfoDto;
    }

    private void saveInvocation(IpapiResponseDto ipapiResponseDto, Double distanceFromBuenosAires) {
        IpStatistics ipStatistics = new IpStatistics();
        ipStatistics.setCountryCode(ipapiResponseDto.getCountry_code());
        ipStatistics.setDistance(distanceFromBuenosAires);
        try {
            statisticsRepository.save(ipStatistics);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la estadística en el repositorio", e);
        }
    }

    private CountryInfoDto buildCountryInfoDto(IpapiResponseDto ipapiResponseDto) {
        CountryInfoDto countryInfoDto = new CountryInfoDto();
        countryInfoDto.setIp(ipapiResponseDto.getIp());
        countryInfoDto.setLocalDateTime(LocalDateTime.now());
        countryInfoDto.setName(ipapiResponseDto.getCountry_name());
        countryInfoDto.setIsoCode(ipapiResponseDto.getCountry_code());
        countryInfoDto.setLanguages(mapLanguages(ipapiResponseDto));
        setCurrencyData(countryInfoDto, ipapiResponseDto);
        countryInfoDto.setTimeZones(utilsComponent.getCurrentTimesByCountry(ipapiResponseDto.getCountry_code(), ipapiResponseDto.getCountry_name()));
        setLocationData(countryInfoDto, ipapiResponseDto);
        return countryInfoDto;
    }

    private List<LanguageDto> mapLanguages(IpapiResponseDto ipapiResponseDto) {
        if (ipapiResponseDto.getLocation() == null || ipapiResponseDto.getLocation().getLanguages() == null) {
            return Collections.emptyList();
        }
        return ipapiResponseDto.getLocation().getLanguages().stream()
                .map(language -> {
                    LanguageDto languageDto = new LanguageDto();
                    languageDto.setName(language.getName());
                    languageDto.setCode(language.getCode());
                    languageDto.setNativeName(language.getNativeName());
                    return languageDto;
                }).toList();
    }

    private void setCurrencyData(CountryInfoDto countryInfoDto, IpapiResponseDto ipapiResponseDto) {
        Currency currency = utilsComponent.getCurrencyByCountryCode(ipapiResponseDto.getCountry_code());
        countryInfoDto.setCurrencyCode(currency.getCurrencyCode());
        countryInfoDto.setCurrencyName(currency.getDisplayName());
        countryInfoDto.setCurrencyExchangeRateWithUsDollar(getCurrencyExchangeRate(currency.getCurrencyCode()));
    }

    private Double getCurrencyExchangeRate(String currencyCode) {
        return utilsComponent.getDollarExchangeRate(fixerClient.getExchangeRates(), currencyCode);
    }

    private void setLocationData(CountryInfoDto countryInfoDto, IpapiResponseDto ipapiResponseDto) {
        double latitude = ipapiResponseDto.getLatitude();
        double longitude = ipapiResponseDto.getLongitude();
        countryInfoDto.setLatitude(latitude);
        countryInfoDto.setLongitude(longitude);
        countryInfoDto.setBuenosAiresLatitude(BUENOS_AIRES_LAT);
        countryInfoDto.setBuenosAiresLongitude(BUENOS_AIRES_LON);
        countryInfoDto.setDistanceToBuenosAires(utilsComponent.getDistanceFromBuenosAires(latitude, longitude));
    }


}

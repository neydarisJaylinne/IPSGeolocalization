package com.example.demo.ServicesTests;

import com.example.demo.Service.IpapiService;
import com.example.demo.clients.IpapiClient;
import com.example.demo.dtos.CountryInfoDto;
import com.example.demo.dtos.IpapiResponseDto;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IpapiServiceTest {

    @Mock
    private IpapiClient ipapiClient;

    @InjectMocks
    private IpapiService ipapiService;

    @Test
    void getCountryDataSuccess(String ip) {
        IpapiResponseDto ipapiResponseDto = new IpapiResponseDto();
        ipapiResponseDto.setCountry_code("AR");
        ipapiResponseDto.setCountry_name("Argentina");

        when(ipapiClient.getCountryData("8.8.8.8")).thenReturn(ipapiResponseDto);

        CountryInfoDto result = ipapiService.getCountryData("8.8.8.8");

        assertNotNull(result);
        assertEquals("Argentina", result.getName());
        assertEquals("AR", result.getIsoCode());
    }

    @Test
    void getCountryDataNull() {
        when(ipapiClient.getCountryData("8.8.8.8")).thenReturn(null);
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            ipapiService.getCountryData("8.8.8.8");
        });
        assertEquals("No se pudo obtener la información de la API para la IP proporcionada.", exception.getMessage());
    }

    @Test
    public void testGetCountryData_MissingCountryCodeOrName_ThrowsNullPointerException() {
        IpapiResponseDto ipapiResponseDto = new IpapiResponseDto();
        ipapiResponseDto.setCountry_code(null);
        ipapiResponseDto.setCountry_name(null);
        when(ipapiClient.getCountryData("8.8.8.8")).thenReturn(ipapiResponseDto);
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            ipapiService.getCountryData("8.8.8.8");
        });
        assertEquals("La respuesta de la API no contiene los datos necesarios.", exception.getMessage());
    }

    @Test
    void getCountryDataDistance_ThrowsServiceException(String ip) {
        when(ipapiClient.getCountryData("8.8.8.8")).thenReturn(null);
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            ipapiService.getCountryData("8.8.8.8");
        });
        assertEquals("No se pudo obtener la información de la API para la IP proporcionada.", exception.getMessage());
    }


}



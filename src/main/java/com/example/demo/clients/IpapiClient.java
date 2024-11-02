package com.example.demo.clients;

import com.example.demo.dtos.IpapiResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class IpapiClient {

    private static final String BASE_URL = "https://api.ipapi.com/api/";
    private static final String ACCESS_KEY = "dcfb1095b17588b7eb9418d22aa82231";

    private final WebClient webClient;
    //private final ObjectMapper objectMapper;

    public IpapiClient() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    public IpapiResponseDto getCountryData(String ip) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(ip)
                            .queryParam("access_key", ACCESS_KEY)
                            .queryParam("language", "es")
                            .build())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                return Mono.error(new RuntimeException("Error in the application when obtaining the IP information: " + response.statusCode()));
                            })
                    .bodyToMono(IpapiResponseDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Error de respuesta HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("Error in the application when obtaining the ip information: ", e);
        } catch (Exception e) {
            System.err.println("Unexpected Error : " + e.getMessage());
            throw new RuntimeException("Unexpected error when obtaining ip information", e);
        }

    }
}

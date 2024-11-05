package com.example.demo.clients;

import com.example.demo.dtos.FixerResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Component
public class FixerClient {

    private static final String BASE_URL = "http://data.fixer.io/api";
    private static final String ACCESS_KEY = "7b6d60d4952b29f48205bcc49b26e4e4";

    private final WebClient webClient;

    public FixerClient() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    public FixerResponseDto getExchangeRates() {
        try {
            return webClient
                    .get().uri(uriBuilder -> uriBuilder.path("/latest")
                            .queryParam("access_key", ACCESS_KEY)
                            .build())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                return Mono.error(new RuntimeException("Error in the application when obtaining the exchange rates: " + response.statusCode()));
                            }
                    )
                    .bodyToMono(FixerResponseDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Error de respuesta HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("Error in the application when obtaining the exchange rates: ", e);
        } catch (Exception e) {
            System.err.println("Unexpected Error : " + e.getMessage());
            throw new RuntimeException("Unexpected error when obtaining ip information", e);
        }
    }
}

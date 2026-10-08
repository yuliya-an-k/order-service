package com.learning.coffee.order_service.client;

import com.learning.coffee.order_service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CatalogClient {

    private final RestClient client;

    @Value("${app.base-url:http://localhost:8088}")
    private String BASE_URL;

    public ProductResponse getProduct(UUID id) {

        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/get-product/" + id)
                .build(true)
                .toUri();

        return client.get()
                .uri(uri)
                .retrieve()
                .body(ProductResponse.class);
    }
}

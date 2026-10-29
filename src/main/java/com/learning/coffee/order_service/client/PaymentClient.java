package com.learning.coffee.order_service.client;

import com.learning.coffee.order_service.dto.PaymentRequest;
import com.learning.coffee.order_service.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestClient client;

    @Value("${app.payment-url:http://localhost:8086}")
    private String PAYMENT_URL;

    public PaymentResponse createPayment(PaymentRequest request) {
        URI uri = UriComponentsBuilder.fromUriString(PAYMENT_URL)
                .path("/payments/create")
                .build(true)
                .toUri();

        return client.post()
                .uri(uri)
                .body(request)
                .retrieve()
                .body(PaymentResponse.class);
    }
}

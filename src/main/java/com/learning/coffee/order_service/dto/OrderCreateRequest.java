package com.learning.coffee.order_service.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequest(
        String customerName,

        @NotEmpty
        List<UUID> productId,

        @NotEmpty
        List<Integer> quantity
) {
}

package com.learning.coffee.order_service.dto;

import java.util.List;
import java.util.UUID;

public record OrderCreate(
        List<UUID> productId
) {
}

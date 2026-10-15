package com.learning.coffee.order_service.dto;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
public class OrderRequestDto {
    private UUID productId;
    private Integer count;
}

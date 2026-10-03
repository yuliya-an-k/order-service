package com.learning.coffee.order_service.dto;

import com.learning.coffee.order_service.entity.OrderItem;
import com.learning.coffee.order_service.entity.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String customerName;

    private OrderStatus status;

    private Instant createdAt;

    private BigDecimal totalAmount;

    private List<OrderItem> items;
}

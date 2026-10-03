package com.learning.coffee.order_service.dto;

import com.learning.coffee.order_service.entity.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    private String customerName;

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItem> items;
}

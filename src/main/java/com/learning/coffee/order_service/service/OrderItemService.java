package com.learning.coffee.order_service.service;

import com.learning.coffee.order_service.dto.ProductResponse;
import com.learning.coffee.order_service.entity.OrderItem;
import com.learning.coffee.order_service.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderItemService {

    public OrderItem create(UUID productId, ProductResponse response, Integer quantity) {
        return OrderItem.builder()
                .productId(productId)
                .productName(response.getProductName())
                .unitPrice(response.getUnitPrice())
                .quantity(quantity)
                .build();
    }
}

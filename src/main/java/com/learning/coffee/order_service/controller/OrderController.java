package com.learning.coffee.order_service.controller;

import com.learning.coffee.order_service.dto.OrderCreateRequest;
import com.learning.coffee.order_service.dto.OrderDto;
import com.learning.coffee.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable UUID id) {
        return orderService.getOrder(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody OrderCreateRequest request) {
        orderService.create(request);
        return ResponseEntity.ok().build();
    }
}

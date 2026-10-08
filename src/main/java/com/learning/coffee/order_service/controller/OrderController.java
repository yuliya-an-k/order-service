package com.learning.coffee.order_service.controller;

import com.learning.coffee.order_service.dto.OrderCreate;
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

    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        return orderService.createOrder(orderCreateRequest);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody OrderCreate request) {
        orderService.create(request);
        return ResponseEntity.ok().build();
    }
}

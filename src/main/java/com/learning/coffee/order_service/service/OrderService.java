package com.learning.coffee.order_service.service;

import com.learning.coffee.order_service.client.CatalogClient;
import com.learning.coffee.order_service.dto.OrderCreateRequest;
import com.learning.coffee.order_service.dto.OrderDto;
import com.learning.coffee.order_service.entity.Order;
import com.learning.coffee.order_service.entity.OrderItem;
import com.learning.coffee.order_service.entity.enumeration.OrderStatus;
import com.learning.coffee.order_service.mapper.OrderMapper;
import com.learning.coffee.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CatalogClient client;
    private final OrderItemService orderItemService;

    public List<OrderDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toOrderDtoList(orders);
    }

    public OrderDto getOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order with ID {} not found", id);
                    return new EntityNotFoundException("Order not found");
                });
        return orderMapper.toOrderDto(order);
    }

    private BigDecimal calculateOrderTotalAmount(List<OrderItem> items) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem orderItem : items) {
            BigDecimal quantity = new BigDecimal(orderItem.getQuantity());
            BigDecimal amount = quantity.multiply(orderItem.getUnitPrice());
            totalAmount = totalAmount.add(amount);
        }
        return totalAmount;
    }

    public void create(OrderCreateRequest request) {
        List<UUID> productIdList = request.productId();
        List<Integer> quantityList = request.quantity();

        List<OrderItem> items = new ArrayList<>();

        for (int i = 0; i < productIdList.size(); i++) {
            UUID productId = productIdList.get(i);
            var response = client.getProduct(productId);
            Integer quantity = quantityList.get(i);
            OrderItem item = orderItemService.create(productId, response, quantity);
            items.add(item);
        }

        Order order = new Order();
        String customerName = request.customerName();
        if (customerName != null) {
            order.setCustomerName(customerName);
        }
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(calculateOrderTotalAmount(items));
        order.setItems(items);
        orderRepository.save(order);
    }
}

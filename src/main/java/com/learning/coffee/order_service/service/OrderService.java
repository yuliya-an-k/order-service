package com.learning.coffee.order_service.service;

import com.learning.coffee.order_service.client.CatalogClient;
import com.learning.coffee.order_service.client.PaymentClient;
import com.learning.coffee.order_service.dto.*;
import com.learning.coffee.order_service.entity.Order;
import com.learning.coffee.order_service.entity.OrderItem;
import com.learning.coffee.order_service.entity.enumeration.OrderStatus;
import com.learning.coffee.order_service.mapper.OrderMapper;
import com.learning.coffee.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CatalogClient client;
    private final OrderItemService orderItemService;
    private final PaymentClient paymentClient;

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

    private PaymentRequest generatePaymentRequest(Order order) {
        Random random = new Random();
        boolean accurate = ((random.nextInt(2) == 1) ? true : false);
        return new PaymentRequest(order.getId(), order.getTotalAmount(), accurate);
    }

    private void checkPayment(PaymentResponse paymentResponse) {
        UUID orderId = paymentResponse.getOrderId();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order with ID {} not found", orderId);
                    return new EntityNotFoundException("Order not found");
                });
        String status = paymentResponse.getStatus();
        if (status.equals("SUCCESSFUL")) {
            order.setStatus(OrderStatus.PAID);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
        }
    }

    @Transactional
    public void create(OrderCreateRequest request) {
        List<OrderRequestDto> productRequests = request.productId();

        Order order = new Order();
        String customerName = request.customerName();
        if (customerName != null) {
            order.setCustomerName(customerName);
        }
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = new ArrayList<>();

        for (OrderRequestDto  req : productRequests) {
            UUID productId = req.getProductId();
            var response = client.getProduct(productId);
            Integer quantity = req.getCount();
            OrderItem item = orderItemService.create(productId, response, quantity);
            item.setOrder(order);
            items.add(item);
        }

        order.setTotalAmount(calculateOrderTotalAmount(items));
        order.setItems(items);
        Order createdOrder = orderRepository.save(order);

        PaymentRequest paymentRequest = generatePaymentRequest(createdOrder);
        PaymentResponse paymentResponse = paymentClient.createPayment(paymentRequest);
        checkPayment(paymentResponse);
    }
}

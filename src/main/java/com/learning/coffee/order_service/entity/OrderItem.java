package com.learning.coffee.order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(
            fetch=FetchType.LAZY
    )
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    private UUID productId;

    @NotNull(message = "Product name is required")
    private String productName;

    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;

    @Positive(message = "Quantity must be positive")
    private int quantity;
}

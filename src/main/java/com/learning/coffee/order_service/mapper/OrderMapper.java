package com.learning.coffee.order_service.mapper;

import com.learning.coffee.order_service.dto.OrderDto;
import com.learning.coffee.order_service.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    List<OrderDto> toOrderDtoList(List<Order> orders);

    OrderDto toOrderDto(Order order);
}

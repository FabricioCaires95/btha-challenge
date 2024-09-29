package br.com.spacer.challengebhtah.domain.dto;

import br.com.spacer.challengebhtah.domain.Order;

import java.math.BigDecimal;

public record OrderResponse(Long customerId, Long orderId, BigDecimal total) {

    public static OrderResponse fromOrder(Order order) {
        return new OrderResponse(order.getCustomerId(), order.getOrderId(), order.getTotal());
    }
}

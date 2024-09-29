package br.com.spacer.challengebhtah.domain.dto;

import br.com.spacer.challengebhtah.domain.Item;
import br.com.spacer.challengebhtah.domain.Order;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreatedEvent(Long orderId, Long customerId, List<OrderItemEvent> itens) {

    public Order convertToOrder() {
        return Order.builder()
                .customerId(customerId)
                .orderId(orderId)
                .total(itens.stream().map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity()))).reduce(BigDecimal.ZERO, BigDecimal::add))
                .items(itens.stream().map(i -> new Item(i.product(), i.quantity(), i.price())).toList())
                .build();
    }
}

package br.com.spacer.challengebhtah.unit;

import br.com.spacer.challengebhtah.domain.Item;
import br.com.spacer.challengebhtah.domain.Order;
import br.com.spacer.challengebhtah.domain.dto.OrderCreatedEvent;
import br.com.spacer.challengebhtah.domain.dto.OrderItemEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public class TestHelper {

    public static OrderCreatedEvent buildWithOneItem() {

        var itens = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
        var event = new OrderCreatedEvent(1L, 2L, List.of(itens));

        return event;
    }

    public static OrderCreatedEvent buildWithTwoItens() {

        var item1 = new OrderItemEvent("notebook", 2, BigDecimal.valueOf(3500.50));
        var item2 = new OrderItemEvent("mouse", 3, BigDecimal.valueOf(50.25));

        var event = new OrderCreatedEvent(1L, 2L, List.of(item1, item2));

        return event;
    }

    public static Order build() {
        var items = new Item("notebook", 1, BigDecimal.valueOf(20.50));

        return Order.builder()
                .customerId(1L)
                .orderId(23L)
                .total(BigDecimal.TEN)
                .items(List.of(items))
                .build();
    }

    public static Page<Order> buildWithPage() {
        return new PageImpl<>(List.of(build()));
    }
}

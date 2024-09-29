package br.com.spacer.challengebhtah.unit;

import br.com.spacer.challengebhtah.domain.dto.OrderCreatedEvent;
import br.com.spacer.challengebhtah.domain.dto.OrderItemEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderCreateEventTest {

    private OrderCreatedEvent orderCreatedEvent;

    @BeforeEach
    void setup() {
        OrderItemEvent orderItemEvent = new OrderItemEvent("Lapis", 3, BigDecimal.valueOf(3));
        List<OrderItemEvent> orderItemEvents = new ArrayList<>();
        orderItemEvents.add(orderItemEvent);

        orderCreatedEvent = new OrderCreatedEvent(1l, 2l, orderItemEvents);
    }

    @Test
    void testGetTotalOrderSuccess() {
        var order = orderCreatedEvent.convertToOrder();

        assertNotNull(order);
        assertEquals(BigDecimal.valueOf(9), order.getTotal());
    }

}

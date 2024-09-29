package br.com.spacer.challengebhtah.unit;

import br.com.spacer.challengebhtah.domain.Item;
import br.com.spacer.challengebhtah.domain.Order;
import br.com.spacer.challengebhtah.domain.dto.OrderCreatedEvent;
import br.com.spacer.challengebhtah.domain.dto.OrderItemEvent;
import br.com.spacer.challengebhtah.repository.OrderRepository;
import br.com.spacer.challengebhtah.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Nested
    class saveOrder {

        @Test
        void shouldCallRepositorySave() {
            var event = TestHelper.buildWithOneItem();

            orderService.saveOrder(event);

            verify(orderRepository, times(1)).save(any(Order.class));
        }

        @Test
        void shouldMapEventToOrderEntitySuccess() {
            var event = TestHelper.buildWithOneItem();

            orderService.saveOrder(event);

            verify(orderRepository, times(1)).save(orderArgumentCaptor.capture());

            var order = orderArgumentCaptor.getValue();

            assertNotNull(order);
            assertEquals(event.orderId(), order.getOrderId());
            assertEquals(event.customerId(), order.getCustomerId());
            assertEquals(1, event.itens().size());
            assertNotNull(order.getTotal());
            assertEquals(event.itens().getFirst().product(), order.getItems().getFirst().getProduct());
            assertEquals(event.itens().getFirst().price(), order.getItems().getFirst().getPrice());
            assertEquals(event.itens().getFirst().quantity(), order.getItems().getFirst().getQuantity());
        }

        @Test
        void shouldCalculateOrderTotalWithSuccess() {
            var event = TestHelper.buildWithTwoItens();

            orderService.saveOrder(event);

            verify(orderRepository, times(1)).save(orderArgumentCaptor.capture());

            var order = orderArgumentCaptor.getValue();

            assertNotNull(order);
            assertNotNull(order.getTotal());
            assertEquals(BigDecimal.valueOf(7151.75), order.getTotal());

        }

    }


    @Nested
    class findAllByCustomerId {

        @Test
        void shouldGetAllOrdersByCustomerIdSuccess() {

            given(orderRepository.findAllByCustomerId(anyLong(), any(PageRequest.class))).willReturn(TestHelper.buildWithPage());

            var orders = orderService.findAllByCustomerId(1l, PageRequest.of(1, 2));

            assertNotNull(orders);

            var orderResponse = orders.get().toList();
            assertNotNull(orderResponse);
            assertEquals(1, orderResponse.size());
        }

    }



}

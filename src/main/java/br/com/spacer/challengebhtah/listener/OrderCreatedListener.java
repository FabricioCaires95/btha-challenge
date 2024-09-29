package br.com.spacer.challengebhtah.listener;

import br.com.spacer.challengebhtah.config.RabbitMqConfig;
import br.com.spacer.challengebhtah.domain.dto.OrderCreatedEvent;
import br.com.spacer.challengebhtah.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void listener(Message<OrderCreatedEvent> message) {
        logger.info("Message received: {}", message);

        orderService.saveOrder(message.getPayload());
    }
}

package br.com.spacer.challengebhtah.controller;

import br.com.spacer.challengebhtah.domain.dto.ApiData;
import br.com.spacer.challengebhtah.domain.dto.OrderResponse;
import br.com.spacer.challengebhtah.domain.dto.PaginationResponse;
import br.com.spacer.challengebhtah.repository.OrderRepository;
import br.com.spacer.challengebhtah.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customer/{customerId}/orders")
    public ResponseEntity<ApiData<OrderResponse>> listOrdersByCustomer(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @PathVariable Long customerId) {

        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOrders = orderService.findTotalOrdersByCustomerId(customerId);

        return ResponseEntity.ok(new ApiData<>(
                Map.of("totalOnOrders", totalOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)));
    }
}
package br.com.spacer.challengebhtah.domain.dto;

import java.math.BigDecimal;

public record OrderItemEvent(String product, Integer quantity, BigDecimal price) {
}

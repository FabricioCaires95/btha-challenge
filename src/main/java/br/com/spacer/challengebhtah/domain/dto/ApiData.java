package br.com.spacer.challengebhtah.domain.dto;

import java.util.List;
import java.util.Map;

public record ApiData<T>(Map<String, Object> summary, List<T> data, PaginationResponse paginationResponse) {
}

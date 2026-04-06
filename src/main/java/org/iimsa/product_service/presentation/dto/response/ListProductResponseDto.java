package org.iimsa.product_service.presentation.dto.response;

import java.util.UUID;

public record ListProductResponseDto(
        UUID productId,
        String productName,
        UUID companyId,
        String companyName,
        UUID hubId,
        String hubName
) {
}

package org.iimsa.product_service.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateProductResponseDto(
        UUID id
) {
}

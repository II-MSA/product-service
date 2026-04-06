package org.iimsa.product_service.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "상품 생성 응답 DTO")
public record CreateProductResponseDto(
        @Schema(description = "생성된 상품의 UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID productId
) {
}

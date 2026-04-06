package org.iimsa.product_service.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "상품 목록 조회 항목 DTO")
public record ListProductResponseDto(

        @Schema(description = "상품 식별자(UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID productId,

        @Schema(description = "상품명", example = "부산 어묵")
        String productName,

        @Schema(description = "소속 업체 식별자(UUID)", example = "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6")
        UUID companyId,

        @Schema(description = "소속 업체명", example = "부산 어묵 생산업체")
        String companyName,

        @Schema(description = "관리 허브 식별자(UUID)", example = "b2c3d4e5-f6g7-8h9i-0j1k-l2m3n4o5p6q7")
        UUID hubId,

        @Schema(description = "관리 허브명", example = "부산 허브")
        String hubName

) {
}

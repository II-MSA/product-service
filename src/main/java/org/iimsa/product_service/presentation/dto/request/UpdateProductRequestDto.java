package org.iimsa.product_service.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "상품 정보 수정 요청 DTO")
public record UpdateProductRequestDto(

        @Schema(description = "수정할 상품명 (수정하지 않을 경우 null)", example = "개선된 유기농 사과")
        String productName,

        @Schema(description = "수정할 업체 식별자 (수정하지 않을 경우 null)", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID companyId

) {
}

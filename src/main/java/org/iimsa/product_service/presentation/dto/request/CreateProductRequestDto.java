package org.iimsa.product_service.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "상품 생성 요청 DTO")
public record CreateProductRequestDto(

        @Schema(description = "등록할 상품의 이름", example = "프리미엄 노트북", requiredMode = Schema.RequiredMode.REQUIRED)
        String productName,

        @Schema(description = "상품을 등록할 업체(Company)의 식별자", example = "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID companyId

) {
}

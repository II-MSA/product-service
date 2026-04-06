package org.iimsa.product_service.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import org.iimsa.product_service.domain.model.Product;

@Schema(description = "상품 상세 정보 응답 DTO")
public record FindProductResponseDto(
        @Schema(description = "상품 식별자", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID productId,

        @Schema(description = "상품명", example = "최고급 유기농 사과")
        String productName,

        @Schema(description = "소속 업체 식별자", example = "a1b2c3d4-...")
        UUID companyId,

        @Schema(description = "소속 업체명", example = "(주)신선물류")
        String companyName,

        @Schema(description = "관리 허브 식별자", example = "b2c3d4e5-...")
        UUID hubId,

        @Schema(description = "관리 허브명", example = "서울 중앙 허브")
        String hubName
) {
    // 변환 로직을 DTO 안으로 캡슐화
    public static FindProductResponseDto from(Product product) {
        var associate = product.getAssociate();
        if (associate == null || associate.getCompany() == null || associate.getHub() == null) {
            throw new IllegalStateException("상품의 연관 정보가 불완전합니다.");
        }
        return new FindProductResponseDto(
                product.getId(),
                product.getProductName(),
                associate.getCompany().getCompanyId(),
                associate.getCompany().getCompanyName(),
                associate.getHub().getHubId(),
                associate.getHub().getHubName()
        );
    }
}

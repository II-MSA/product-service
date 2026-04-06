package org.iimsa.product_service.presentation.dto.response;

import java.util.UUID;
import org.iimsa.product_service.domain.model.Product;

public record FindProductResponseDto(
        UUID productId,
        String productName,
        UUID companyId,
        String companyName,
        UUID hubId,
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
                associate.getCompany().getId(),
                associate.getCompany().getName(),
                associate.getHub().getId(),
                associate.getHub().getName()
        );
    }
}

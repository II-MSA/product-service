package org.iimsa.product_service.application.dto.command;

import java.util.UUID;
import org.iimsa.product_service.domain.service.CompanyInfoResult;
import org.iimsa.product_service.application.dto.result.CompanyResponseDto;
import org.iimsa.product_service.domain.model.Product;
import org.iimsa.product_service.presentation.dto.request.CreateProductRequestDto;

public record CreateProductCommand(
        String productName,
        UUID companyId,
        String companyName,
        UUID hubId,
        String hubName
) {
    // 1. Controller에서 DTO와 외부 데이터를 조합해 생성할 때 사용할 정적 팩토리 메서드
    public static CreateProductCommand of(CreateProductRequestDto requestDto, CompanyInfoResult companyInfo) {
        return new CreateProductCommand(
                requestDto.productName(),
                requestDto.companyId(),
                companyInfo.companyName(),
                companyInfo.hubId(),
                companyInfo.hubName()
        );
    }

    // 2. Command -> Entity 변환 (재료가 모두 있으므로 builder로 매핑)
    public Product toEntity() {
        return Product.builder()
                .productName(this.productName)
                .companyId(this.companyId)
                .companyName(this.companyName)
                .hubId(this.hubId)
                .hubName(this.hubName)
                .build();
    }
}

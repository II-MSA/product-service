package org.iimsa.product_service.presentation.dto.request;

import java.util.UUID;

public record UpdateProductRequestDto(
        String productName, // 수정 안 할 경우 null로 들어옴
        UUID companyId      // 수정 안 할 경우 null로 들어옴
) {}

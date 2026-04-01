package org.iimsa.product_service.presentation.dto.request;

import java.util.UUID;

public record CreateProductRequestDto(
        String productName,
        UUID companyId
){

}

package org.iimsa.product_service.application.dto.command;

import java.util.UUID;
import org.iimsa.product_service.presentation.dto.request.UpdateProductRequestDto;

public record UpdateProductCommand(
        String productName,
        UUID companyId
) {
    public static UpdateProductCommand from(UpdateProductRequestDto requestDto) {
        return new UpdateProductCommand(requestDto.productName(), requestDto.companyId());
    }
}

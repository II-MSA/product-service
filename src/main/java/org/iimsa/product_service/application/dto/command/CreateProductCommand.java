package org.iimsa.product_service.application.dto.command;

import java.util.UUID;

public record CreateProductCommand(
        String productName,
        UUID companyId
) {
}

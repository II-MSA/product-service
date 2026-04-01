package org.iimsa.product_service.domain.service;

import java.util.UUID;

public record CompanyInfoResult(
        String companyName,
        UUID hubId,
        String hubName
) {
}

package org.iimsa.product_service.domain.service;

import java.util.UUID;

public interface CompanyProvider {
    CompanyInfoResult getCompany(UUID companyId);
}

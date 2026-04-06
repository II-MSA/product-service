package org.iimsa.product_service.domain.service;

import java.util.UUID;
import org.iimsa.product_service.domain.service.dto.CompanyData;

public interface CompanyProvider {
    CompanyData getCompany(UUID companyId);
}

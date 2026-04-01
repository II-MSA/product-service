package org.iimsa.product_service.infrastructure.provider;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.product_service.domain.service.CompanyInfoResult;
import org.iimsa.product_service.domain.service.CompanyProvider;
import org.iimsa.product_service.infrastructure.client.CompanyClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyProviderImpl implements CompanyProvider {
    private final CompanyClient companyClient;

    @Override
    public CompanyInfoResult getCompany(UUID companyId) {
        return companyClient.getCompany(companyId);
    }
}

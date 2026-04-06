package org.iimsa.product_service.infrastructure.client;

import java.util.UUID;
import org.iimsa.product_service.domain.service.dto.CompanyData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "company-service",
        fallbackFactory = CompanyClientFallbackFactory.class
)
public interface CompanyClient {
    @GetMapping("/api/v1/companies/{companyId}")
    CompanyData getCompany(@PathVariable("companyId") UUID companyId);
}

package org.iimsa.product_service.infrastructure.client;

import org.iimsa.product_service.domain.service.CompanyInfoResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

// 업체 서비스의 Feign 클라이언트
@FeignClient(name = "company-service")
public interface CompanyClient {

    @GetMapping("/api/v1/companies/{companyId}")
    CompanyInfoResult getCompany(@PathVariable("companyId") UUID companyId);
}

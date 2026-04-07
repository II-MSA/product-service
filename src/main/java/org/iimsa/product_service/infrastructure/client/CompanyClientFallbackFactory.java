package org.iimsa.product_service.infrastructure.client;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class CompanyClientFallbackFactory implements FallbackFactory<CompanyClient> {
    @Override
    public CompanyClient create(Throwable e) {
        return companyId -> {
            log.error("[Company Service Fallback] ID: {} 조회 중 장애 발생. 사유: {}", companyId, e.getMessage(), e);

            if (e instanceof FeignException feignException) {
                HttpStatus upstreamStatus = HttpStatus.resolve(feignException.status());
                if (upstreamStatus != null && upstreamStatus.is4xxClientError()) {
                    throw new ResponseStatusException(
                            upstreamStatus,
                            "Company Service 요청 실패",
                            e
                    );
                }
            }
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Company Service API 요청 처리 실패, 잠시 후 다시 시도해주세요.", e);
        };
    }
}

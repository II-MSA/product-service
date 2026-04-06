package org.iimsa.product_service.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iimsa.product_service.domain.service.CompanyInfoResult;
import org.iimsa.product_service.domain.service.CompanyProvider;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Associate {
    @Embedded
    private Company company;

    @Embedded
    private Hub hub;

    protected Associate(UUID companyId, CompanyProvider provider) {
        if (companyId == null || provider == null) {
            throw new IllegalArgumentException("업체 ID 및 처리 Provider는 필수 항목 입니다.");
        }

        CompanyInfoResult result = provider.getCompany(companyId);
        if (result == null) {
            throw new IllegalArgumentException("업체를 찾을수 없습니다. ID: " + companyId.toString());
        }

        this.company = new Company(companyId, result.companyName());
        this.hub = new Hub(result.hubId(), result.hubName());
    }
}

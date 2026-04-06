package org.iimsa.product_service.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iimsa.product_service.domain.service.CompanyProvider;
import org.iimsa.product_service.domain.service.dto.CompanyData;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Associate {
    @Embedded
    private Company company;

    @Embedded
    private Hub hub;

    protected Associate(UUID companyId, CompanyProvider companyProvider) {
        if (companyId == null || companyProvider == null) {
            throw new IllegalArgumentException("업체 ID 및 처리 Provider는 필수 항목 입니다.");
        }

        CompanyData companyData = companyProvider.getCompany(companyId);
        if (companyData == null) {
            throw new IllegalArgumentException("업체를 찾을수 없습니다. ID: " + companyId.toString());
        }

        this.company = new Company(companyId, companyProvider);
        this.hub = new Hub(companyData.hubId(), companyProvider);
    }

    public static Associate from(UUID companyId, CompanyProvider companyProvider) {
        return new Associate(companyId, companyProvider);
    }

    // 외부에 제공할 편의 메서드 (이전 답변의 권한 체크에서 활용)
    public UUID getCompanyId() {
        return this.company.getCompanyId();
    }

    public UUID getHubId() {
        return this.hub.getHubId();
    }
}

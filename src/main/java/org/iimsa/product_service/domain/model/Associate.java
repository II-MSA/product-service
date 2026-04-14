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
            throw new IllegalArgumentException("업체를 찾을수 없습니다. ID: " + companyId);
        }

        this.company = new Company(companyId, companyData);   // CompanyData 재사용
        this.hub = new Hub(companyData);                      // CompanyData 재사용, hubId가 아닌 companyData 그대로 전달
    }

    public static Associate from(UUID companyId, CompanyProvider companyProvider) {
        return new Associate(companyId, companyProvider);
    }

    // 외부에 제공할 편의 메서드 (이전 답변의 권한 체크에서 활용)
    public UUID getCompanyId() {
        if (this.company == null) {
            return null;
        }
        return this.company.getCompanyId();
    }

    public UUID getHubId() {
        if (this.hub == null) {
            return null;
        }
        return this.hub.getHubId();
    }
}

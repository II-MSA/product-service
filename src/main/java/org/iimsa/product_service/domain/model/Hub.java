package org.iimsa.product_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class Hub {
    @Column(length = 36, name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(length = 80, name = "hub_name", nullable = false)
    private String hubName;


    protected Hub(CompanyData companyData) {
        if (companyData == null) {
            throw new IllegalArgumentException("companyData cannot be null");
        }
        if (companyData.hubId() == null) {
            throw new IllegalArgumentException("이 업체에 할당된 허브가 없습니다.");
        }
        this.hubId = companyData.hubId();
        this.hubName = companyData.hubName();
    }

    protected static Hub from(UUID companyId, CompanyProvider companyProvider) {
        CompanyData companyData = companyProvider.getCompany(companyId);
        return new Hub(companyData);
    }
}

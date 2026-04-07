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


    protected Hub(UUID companyId, CompanyProvider provider) {
        if (hubId == null) {
            throw new IllegalArgumentException("hubId cannot be null");
        }
        if (provider == null) {
            throw new IllegalArgumentException("provider cannot be null");
        }
        CompanyData companyData = provider.getCompany(companyId);
        if (companyData == null) {
            throw new IllegalArgumentException("companyData cannot be null");
        }
        this.hubId = companyData.hubId();
        this.hubName = companyData.hubName();
        if (this.hubId == null) {
            throw new IllegalArgumentException("This company does not have an assigned hub");
        }
    }

    protected static Hub from(UUID companyId, CompanyProvider companyProvider) {
        return new Hub(companyId, companyProvider);
    }
}

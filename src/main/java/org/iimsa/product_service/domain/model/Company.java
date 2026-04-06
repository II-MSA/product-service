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
public class Company {
    @Column(length = 36, name = "company_id", nullable = false)
    private UUID companyId;

    @Column(length = 80, name = "company_name", nullable = false)
    private String companyName;

    protected Company(UUID companyId, CompanyProvider provider) {
        if (companyId == null) {
            throw new IllegalArgumentException("receiverId is null");
        }
        if (provider == null) {
            throw new IllegalArgumentException("provider is null");
        }
        this.companyId = companyId;
        CompanyData companyData = provider.getCompany(companyId);
        if (companyData == null) {
            throw new IllegalArgumentException("companyData is null");
        }
        this.companyName = companyData.companyName();
    }

    public static Company from(UUID companyId, CompanyProvider companyProvider) {
        return new Company(companyId, companyProvider);
    }
}

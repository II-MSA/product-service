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

    protected Company(UUID companyId, CompanyData companyData) {
        if (companyId == null) {
            throw new IllegalArgumentException("companyId is null");
        }
        if (companyData == null || companyData.companyName() == null || companyData.companyName().isBlank()) {
            throw new IllegalArgumentException("companyData is null or invalid");
        }
        this.companyId = companyId;
        this.companyName = companyData.companyName();
    }

    public static Company from(UUID companyId, CompanyProvider companyProvider) {
        CompanyData companyData = companyProvider.getCompany(companyId);
        return new Company(companyId, companyData);
    }
}

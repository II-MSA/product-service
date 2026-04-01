package org.iimsa.product_service.domain.service;

import java.util.List;
import java.util.UUID;
import org.iimsa.product_service.domain.model.Company;

public interface RoleCheck {
    boolean hasRole(String role);
    boolean hasRole(List<String> roles);
    boolean isMyCompany(UUID companyId);
}

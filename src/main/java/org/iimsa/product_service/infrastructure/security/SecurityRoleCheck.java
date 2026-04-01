package org.iimsa.product_service.infrastructure.security;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.product_service.domain.service.RoleCheck;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityRoleCheck implements RoleCheck {
    @Override
    public boolean hasRole(String role) {
        return false;
    }

    @Override
    public boolean hasRole(List<String> roles) {
        return false;
    }

    @Override
    public boolean isMyCompany(UUID companyId) {
        return false;
    }
}

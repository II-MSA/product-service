package org.iimsa.product_service.domain.security;

import java.util.List;
import org.iimsa.product_service.domain.model.UserType;

public interface RoleCheck {
    // 단일 권한 확인
    boolean hasRole(UserType type);

    boolean hasRole(List<UserType> types);

//    // 내 업체인지 확인
//    boolean isMyCompany(UUID targetCompanyId);
//
//    // 내 허브인지 확인
//    boolean isMyHub(UUID targetHubId);
}

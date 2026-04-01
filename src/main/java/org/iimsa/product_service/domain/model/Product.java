package org.iimsa.product_service.domain.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import java.util.UUID;
import org.iimsa.product_service.domain.service.CompanyProvider;
import org.iimsa.product_service.domain.service.RoleCheck;


// 재고 필드가 없고, 재고는 허브 상품(p_hub_product)에서 관리한다.
// p_product는 일종의 카탈로그 개념으로써, 어떤 상품이 있는 지 목록을 제공한다.
// 이 엔티티 정보를 허브 서비스와 주문 서비스 등에서 요청하게 된다.
@Entity
@Table(name = "p_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String productName;

    @Embedded
    private Associate associate;

    @Builder
    public Product(String productName, UUID companyId, CompanyProvider  companyProvider, RoleCheck roleCheck) {
        // 등록권한 체크
        checkAuthority(roleCheck);

        this.productName = productName;
        this.associate = new Associate(companyId, companyProvider);
    }

    /**
     * 상품 생성을 위한 정적 팩토리 메서드
     */
    public static Product create(
            String productName,
            UUID companyId,
            CompanyProvider companyProvider
    ) {
        return Product.builder()
                .productName(productName)
                .companyId(companyId)
                .companyProvider(companyProvider)
                .build();
    }

    // ===========================
    // 비즈니스 로직 (수정 등)
    // 아래 코드는 이후 제거 or 수정 or 추가 가능성 있음
    // ===========================

    public void updateInfo(String productName, UUID companyId, CompanyProvider companyProvider, RoleCheck roleCheck) {
        // 수정 권한 체크
        checkAuthority(roleCheck);

        this.productName = productName;
        this.associate = new Associate(companyId, companyProvider);
    }

    private void checkAuthority(RoleCheck roleCheck) {
        if (roleCheck.hasRole("MASTER")) { // MASTER 관리자는 권한 체크 필요 없음
            return;
        }

        if (!(roleCheck.hasRole("COMPANY_MANAGER") && roleCheck.isMyCompany(this.associate.getCompany().getId()))) {
            throw new RuntimeException("처리할 권한이 없습니다.");
        }
    }

    // 업체나 허브가 변경되는 경우를 위한 로직
//    public void changeAffiliation(UUID companyId, UUID hubId) {
//        this.companyId = companyId;
//        this.hubId = hubId;
//    }
}

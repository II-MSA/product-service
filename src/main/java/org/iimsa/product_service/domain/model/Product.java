package org.iimsa.product_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.product_service.domain.security.RoleCheck;


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
    public Product(
            String productName,
            Associate associate,
            RoleCheck roleCheck) {
        // 권한체크
        checkAuthority(roleCheck);
        this.productName = productName;
        this.associate = associate;
    }

    /**
     * 상품 생성을 위한 정적 팩토리 메서드
     */
    public static Product create(
            String productName,
            Associate associate) {
        return Product.builder()
                .productName(productName)
                .associate(associate)
                .build();
    }

    // ===========================
    // 비즈니스 로직 (수정 등)
    // 아래 코드는 이후 제거 or 수정 or 추가 가능성 있음
    // ===========================

    // 상품 정보 전체 수정
    public void updateProduct(String productName, Associate associate, RoleCheck roleCheck) {

        checkAuthority(roleCheck);

        // 상품명이 들어온 경우 수정
        if (productName != null && !productName.isBlank()) {
            this.productName = productName;
        }

        // 업체 정보가 들어온 경우 수정
        if (associate != null) {
            this.associate = associate;
        }
    }

    // 상품명만 수정
    public void updateProductName(String productName, RoleCheck roleCheck) {
        checkAuthority(roleCheck);

        roleCheck.hasRole(UserType.MASTER);
        if (productName != null && !productName.isBlank()) {
            this.productName = productName;
        }
    }

    // 소속만 수정
    public void updateAssociate(Associate associate, RoleCheck roleCheck) {
        // 본인 담당 업체인 지 체크
        checkAuthority(roleCheck);
        if (associate != null) {
            this.associate = associate;
        }
    }

    /**
     * MASTER, HUB_MANAGER, COMPANY_MANAGER 중 하나라도 있으면 허용
     */
    private void checkAuthority(RoleCheck roleCheck) {
        if (roleCheck == null) {
            throw new RuntimeException("권한 확인을 위한 RoleCheck 객체가 필요합니다.");
        }

        boolean isAuthorized = roleCheck.hasRole(UserType.MASTER) ||
                roleCheck.hasRole(UserType.HUB_MANAGER) ||
                roleCheck.hasRole(UserType.COMPANY_MANAGER);

        if (!isAuthorized) {
            throw new RuntimeException("상품 처리에 필요한 권한이 없습니다.");
        }
    }
}

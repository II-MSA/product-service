package org.iimsa.product_service.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.product_service.application.dto.command.UpdateProductCommand;
import org.iimsa.product_service.domain.service.CompanyProvider;
import org.iimsa.product_service.domain.model.Product;
import org.iimsa.product_service.domain.repository.ProductRepository;
import org.iimsa.product_service.domain.service.RoleCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CompanyProvider companyProvider;
    private final RoleCheck roleCheck;

    @Transactional
    public UUID createProduct(String productName, UUID companyId) {

        Product product = Product.builder()
                .productName(productName)
                .companyId(companyId)
                .companyProvider(companyProvider)
                .roleCheck(roleCheck)
                .build();

        Product result = productRepository.save(product);

        return result.getId();
    }

    public Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다. ID: " + productId));
    }

    @Transactional
    public void updateProduct(UUID productId, UpdateProductCommand command) {
        // 1. 기존 데이터 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다. ID: " + productId));

        // 2. 도메인 모델에 수정 위임
        // command 내부의 필드가 null이어도 엔티티에서 방어 로직이 작동함
        product.updateInfo(
                command.productName(),
                command.companyId(),
                companyProvider,
                roleCheck
        );
    }
}

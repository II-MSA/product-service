package org.iimsa.product_service.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.product_service.application.dto.command.CreateProductCommand;
import org.iimsa.product_service.application.dto.command.UpdateProductCommand;
import org.iimsa.product_service.domain.model.Associate;
import org.iimsa.product_service.domain.model.Product;
import org.iimsa.product_service.domain.repository.ProductRepository;
import org.iimsa.product_service.domain.security.RoleCheck;
import org.iimsa.product_service.domain.service.CompanyProvider;
import org.iimsa.product_service.presentation.dto.response.ListProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CompanyProvider companyProvider;
    private final RoleCheck roleCheck;

    public UUID createProduct(CreateProductCommand command) {
        Associate associate = Associate.from(command.companyId(), companyProvider);

        Product product = Product.create(
                command.productName(),
                associate
        );
        return productRepository.save(product).getId();
    }

    @Transactional(readOnly = true)
    public Product getProduct(UUID productId) {
        return findProductById(productId);
    }

    public void updateProduct(UUID productId, UpdateProductCommand command) {
        Product product = findProductById(productId);

        Associate associate = Associate.from(command.companyId(), companyProvider);
        product.updateProduct(
                command.productName(),
                associate,
                roleCheck
        );
    }

    public void updateProductName(UUID productId, UpdateProductCommand command) {
        Product product = findProductById(productId);

        String productName = command.productName();
        product.updateProductName(productName, roleCheck);
    }

    public void updateAssociate(UUID productId, UpdateProductCommand command) {
        Product product = findProductById(productId);

        Associate associate = Associate.from(command.companyId(), companyProvider);
        product.updateAssociate(associate, roleCheck);
    }

    public void deleteProductById(UUID productId) {
        productRepository.deleteProductById(productId);
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다. ID: " + productId));
    }

    @Transactional(readOnly = true)
    public Page<ListProductResponseDto> searchProducts(Pageable pageable) {
        // 1. 레포지토리에서 Page<Product>를 가져옵니다.
        Page<Product> productPage = productRepository.findAll(pageable);

        // 2. .map()을 사용하여 각 Product 엔티티를 ListProductResponseDto로 변환합니다.
        return productPage.map(product -> new ListProductResponseDto(
                product.getId(),
                product.getProductName(),
                product.getAssociate().getCompany().getCompanyId(),  // Associate 객체 구조에 맞춰 필드 호출
                product.getAssociate().getCompany().getCompanyName(), // 만약 Associate에 회사명이 있다면 호출
                product.getAssociate().getHub().getHubId(),
                product.getAssociate().getHub().getHubName()
        ));
    }
}

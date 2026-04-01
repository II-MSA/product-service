package org.iimsa.product_service.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
        return productRepository.findById(productId);
    }
}

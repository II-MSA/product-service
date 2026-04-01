package org.iimsa.product_service.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.iimsa.product_service.domain.model.Product;
import org.iimsa.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        return jpaProductRepository.save(product);
    }
}

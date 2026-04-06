package org.iimsa.product_service.domain.repository;

import java.util.Optional;
import java.util.UUID;
import org.iimsa.product_service.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(UUID productId);

    void deleteProductById(UUID productId);

    Page<Product> findAll(Pageable pageable);
}

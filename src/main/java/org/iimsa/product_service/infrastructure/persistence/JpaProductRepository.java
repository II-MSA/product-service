package org.iimsa.product_service.infrastructure.persistence;

import java.util.UUID;
import org.iimsa.product_service.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, UUID> {
}

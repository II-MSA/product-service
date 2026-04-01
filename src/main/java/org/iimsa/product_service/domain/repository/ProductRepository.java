package org.iimsa.product_service.domain.repository;

import java.util.UUID;
import org.iimsa.product_service.domain.model.Product;

public interface ProductRepository {
    Product save(Product product);
}

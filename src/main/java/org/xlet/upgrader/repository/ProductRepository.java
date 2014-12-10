package org.xlet.upgrader.repository;

import org.xlet.upgrader.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-4 下午8:25
 * Summary:
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Product findByName(String productName);

    Product findByCode(String code);

}

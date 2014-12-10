package org.xlet.upgrader.service;

import org.xlet.upgrader.domain.Product;
import org.xlet.upgrader.vo.dashboard.ProductDTO;
import org.xlet.upgrader.vo.dashboard.form.ProductForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 上午11:14
 * Summary:
 */
public interface ProductService {

    public Page<ProductDTO> list(Pageable pageable);

    ProductDTO getProduct(Long id);

    void saveProduct(Product product);

    void deleteProduct(Long id);

    void updateProduct(ProductForm product, Long id);

    List<ProductDTO> all();

    boolean checkCode(String code);

    Product getTimeLine(String product);

}

package org.xlet.upgrader.service.impl;

import org.xlet.upgrader.domain.Product;
import org.xlet.upgrader.exception.TargetNotFoundException;
import org.xlet.upgrader.repository.ProductRepository;
import org.xlet.upgrader.service.ProductService;
import org.xlet.upgrader.vo.dashboard.ProductDTO;
import org.xlet.upgrader.vo.dashboard.form.ProductForm;
import com.google.common.collect.Lists;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 上午11:16
 * Summary:
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private Mapper dozer;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductDTO> list(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductDTO> dtoList = Lists.newArrayList();
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            dtoList.add(dozer.map(iterator.next(), ProductDTO.class));
        }
        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    @Override
    public ProductDTO getProduct(Long id) {
        Product one = productRepository.findOne(id);
        return dozer.map(one, ProductDTO.class);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    @Override
    public void updateProduct(ProductForm product, Long id) {
        Product dbProduct = productRepository.findOne(id);
        dozer.map(product, dbProduct);
        productRepository.save(dbProduct);
    }

    @Override
    public List<ProductDTO> all() {
        Iterable<Product> products = productRepository.findAll();
        Iterator<Product> iterator = products.iterator();
        List<ProductDTO> dtoList = Lists.newArrayList();
        while (iterator.hasNext()) {
            dtoList.add(dozer.map(iterator.next(), ProductDTO.class));
        }
        return dtoList;
    }

    @Override
    public boolean checkCode(String code) {
        return productRepository.findByCode(code) == null;
    }

    @Override
    public Product getTimeLine(String code) {
        Product product = productRepository.findByCode(code);
        if (product == null) throw new TargetNotFoundException();
        return product;
    }
}

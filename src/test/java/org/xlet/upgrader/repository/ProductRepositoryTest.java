package org.xlet.upgrader.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xlet.upgrader.Application;
import org.xlet.upgrader.domain.Product;
import org.xlet.upgrader.domain.Version;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-5 下午5:23
 * Summary:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    VersionRepository versionRepository;


    //@Test
    public void testAdd(){
        Product product = new Product();
        product.setName("liuliu");
        product.setHomepage("http://im.w.cn/liuliu");
        productRepository.save(product);
    }

   // @Test
    public void testUpdate(){
        Product one = productRepository.findOne(1L);
        one.setName("i'm im");
        productRepository.save(one);
    }

    //@Test
    public void testFind(){
        Product product = productRepository.findByCode("im");
        Version version = versionRepository.findByProductAndVersion(product, "0.0.0.1");
    }
}

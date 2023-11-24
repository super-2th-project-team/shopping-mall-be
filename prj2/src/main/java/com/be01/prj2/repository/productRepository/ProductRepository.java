package com.be01.prj2.repository.productRepository;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByProductId(Long productId);
    Long findUserIdByProductId(Long productId);
    List<Product> findProductBySellerId(Customer userId);
}

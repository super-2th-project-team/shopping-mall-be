package com.be01.prj2.repository.productRepository;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    ArrayList<?> findByProductId(Long productId);


    Long findUserIdByProductId(Long productId);

    List<Product> findProductBySellerId(Customer userId);
}

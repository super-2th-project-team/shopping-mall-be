package com.be01.prj2.repository.productRepository;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByProductId(Long productId);


    Long findUserIdByProductId(Long productId);

    List<Product> findProductBySellerId(Customer userId);
}

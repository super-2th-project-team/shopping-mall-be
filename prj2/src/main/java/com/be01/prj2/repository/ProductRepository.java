package com.be01.prj2.repository;

import com.be01.prj2.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    ArrayList<?> findByProductId(Long productId);


}

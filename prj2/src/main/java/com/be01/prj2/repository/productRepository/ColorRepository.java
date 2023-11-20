package com.be01.prj2.repository.productRepository;

import com.be01.prj2.entity.product.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColorRepository extends JpaRepository<ProductColor, Long> {

    @Query("SELECT DISTINCT c.color FROM ProductColor c WHERE c.productId = :productId")
    List<String> findDistinctColorsByProductId(@Param("productId") Long productId);
}

package com.be01.prj2.repository.productRepository;

import com.be01.prj2.entity.product.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SizeRepository extends JpaRepository<ProductSize, Long> {
    @Query("SELECT distinct c.size FROM ProductSize c WHERE c.productId = :productId")
    List<String> findDistinctSizesByProductId(@Param("productId") Long productId);
}

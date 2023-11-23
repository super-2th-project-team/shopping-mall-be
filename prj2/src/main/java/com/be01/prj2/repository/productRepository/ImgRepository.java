package com.be01.prj2.repository.productRepository;

import com.be01.prj2.entity.product.ProductImg;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface ImgRepository extends JpaRepository<ProductImg, Long> {

    @Query("SELECT DISTINCT c.img FROM ProductImg c WHERE c.productId = :productId")
    List<String> findDistinctImgsByProductId(@Param("productId") Long productId);

}

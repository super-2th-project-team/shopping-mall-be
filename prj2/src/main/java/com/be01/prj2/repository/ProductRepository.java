package com.be01.prj2.repository;

import com.be01.prj2.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
    public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    }

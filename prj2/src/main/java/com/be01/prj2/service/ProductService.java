package com.be01.prj2.service;

import com.be01.prj2.entity.ProductEntity;
import com.be01.prj2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }
    // 상품의 재고 감소 메서드
    public void decreaseProductStock(Long productId, int quantity) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        // 재고 감소
        int currentStock = product.getProductStock();
        if (currentStock < quantity) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        product.setProductStock(currentStock - quantity);

        // 감소된 재고를 저장
        productRepository.save(product);
    }

}
package com.be01.prj2.service;

import com.be01.prj2.entity.ProductEntity;
import com.be01.prj2.repository.ProductRepository;
import com.be01.prj2.web.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public int getProductStock(Long productId) {
        // 상품의 재고를 데이터베이스에서 조회하여 반환하는 로직
        productRepository.findById(productId).map(ProductEntity::getProductStock).orElse(0);
        // 실제 데이터베이스와의 상호 작용에 맞게 수정해야 합니다.
        return 0;
    }
    public int getProductPrice(Long productId) {
        // 상품의 가격을 데이터베이스에서 조회하여 반환하는 로직
        productRepository.findById(productId).map(ProductEntity::getProductPrice).orElse(0);
        // 실제 데이터베이스와의 상호 작용에 맞게 수정해야 합니다.
        return 0;
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
            throw new RuntimeException(" 재고가 부족합니다.");
        }
        product.setProductStock(currentStock - quantity);

        // 감소된 재고를 저장
        productRepository.save(product);
    }

}
package com.be01.prj2.service.purchase;


import com.be01.prj2.repository.purchase.ProductRepository;
import com.be01.prj2.dto.purchase.OrderRequest;
import com.be01.prj2.service.purchase.UserService;
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

    public boolean checkInventoryAndBudget(OrderRequest orderRequest) {
        Long productId = orderRequest.getProductId();
        int orderQuantity = orderRequest.getOrderQuantity();

        // 상품의 현재 재고 확인
        int productStock = getProductStock(productId);

        // 사용자의 예산 확인
        Long userId = orderRequest.getUserIdx();
        int userBudget = userService.getUserBudget(userId);

        // 주문하려는 상품의 총 가격 확인
        int orderTotalPrice = getProductPrice(productId) * orderQuantity;

        // 주문 가능 여부 판단
        return userBudget >= orderTotalPrice && productStock >= orderQuantity;
    }
}
//package com.be01.prj2.productTest;
//
//import com.be01.prj2.entity.ProductEntity;
//import com.be01.prj2.service.ProductService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class ProductServiceTest {
//
//    @Autowired
//    private ProductService productService;
//
//    @Test
//    public void decreaseProductStockTest() {
//        // Given
//        Long productId = 1L;
//        int initialStock = 10; // 초기 재고
//        int orderQuantity = 5; // 주문 수량
//
//        // When
//        ProductEntity product = new ProductEntity();
//        product.setProductId(productId);
//        product.setProductStock(initialStock);
//
//        productService.decreaseProductStock(product, orderQuantity);
//
//        // Then
//        assertEquals(initialStock - orderQuantity, product.getProductStock());
//    }
//}

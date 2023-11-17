package com.be01.prj2.service;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.Customer;
import com.be01.prj2.entity.category.Category;
import com.be01.prj2.entity.product.Product;

import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.CategoryRepository;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product productRegister(SellDto sellDto, String email) {
        Optional<Customer> seller = customerRepository.findByEmail(email);

        if (seller.isPresent()) {

            Category category = getCategoryOrCreateIfNotExists(String.valueOf(sellDto.getCategory()));

            Product product = Product.builder()
                    .sellerId(sellDto.getSellerID())
                    .productName(sellDto.getProductName())
                    .productPrice(sellDto.getProductPrice())
                    .productInfo(sellDto.getProductInfo())
                    .productStock(sellDto.getProductStock())
                    .productSell(sellDto.getProductSell())
                    .productEnroll(sellDto.getProductEnroll())
                    .productImg(sellDto.getProductImg())
                    .category(category)
                    .build();
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    private Category getCategoryOrCreateIfNotExists(String categoryName) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryName);

        return existingCategory.orElseGet(() -> {
            // 저장되지 않은 Category인 경우 새로 생성하여 저장
            Category newCategory = Category.builder().categoryName(categoryName).build();
            return categoryRepository.save(newCategory);
        });
    }
}

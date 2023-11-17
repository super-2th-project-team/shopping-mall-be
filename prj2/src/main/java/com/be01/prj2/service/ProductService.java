package com.be01.prj2.service;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.Customer;
import com.be01.prj2.entity.product.Product;

import com.be01.prj2.jwt.TokenProvider;

import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;

    @Transactional
    public Product productRegister(SellDto sellDto, Long userId) {
        Optional<Customer> isSeller = customerRepository.findByUserId(userId);

        if (isSeller.isPresent()) {
            Customer customer = isSeller.get();

            List<String> color = Arrays.asList("red", "blue", "green", "beige");
            List<String> size = Arrays.asList("s", "m", "L", "XL", "FREE");


            Product product = Product.builder()
                    .productName(sellDto.getProductName())
                    .productPrice(sellDto.getProductPrice())
                    .productInfo(sellDto.getProductInfo())
                    .productStock(sellDto.getProductStock())
                    .productSell(sellDto.getProductSell())
                    .productEnroll(sellDto.getProductEnroll())
                    .productImg(sellDto.getProductImg())
                    .category(sellDto.getCategory())
                    .subCategory(sellDto.getSubCategory())
                    .color(color.subList(0, 3))
                    .size(size.subList(0, 4))
                    .userId(customer)
                    .build();
            return productRepository.save(product);
        } else {
            return null;
        }
    }

}

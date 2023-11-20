package com.be01.prj2.service.ProductService;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;

import com.be01.prj2.jwt.TokenProvider;

import com.be01.prj2.repository.ColorRepository;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.repository.ProductRepository;
import com.be01.prj2.repository.SizeRepository;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    //판매 물품 등록
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
                    .color(color.subList(0, 4))
                    .size(size.subList(0, 5))
                    .sellerId(customer)
                    .build();
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    //DB에 있는 모든 물품 조회
    public Page<Product> findAll(Pageable pageable){

        return productRepository.findAll(pageable);
    }

    @Transactional
    public Map<String, List<String>> getProductDetails(Long productId) {
        Map<String, List<String>> productDetails = new HashMap<>();

        List<String> colorList = colorRepository.findDistinctColorsByProductId(productId);
        List<String> sizeList = sizeRepository.findDistinctSizesByProductId(productId);

        productDetails.put("colors", colorList);
        productDetails.put("sizes", sizeList);

        return productDetails;
    }

    //productId로 userId를 가지고 오는 로직
    public Long findUserIdByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && product.getSellerId() != null) {
            return product.getSellerId().getUserId();
        } else {
            return null;
        }
    }





}

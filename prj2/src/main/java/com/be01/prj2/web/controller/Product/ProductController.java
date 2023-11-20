package com.be01.prj2.web.controller.Product;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.repository.ProductRepository;
import com.be01.prj2.service.SellService.SellService;
import com.be01.prj2.service.customerService.CustomerService;
import com.be01.prj2.service.ProductService.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;
    private final SellService sellService;

    //토큰을 받아서 상품 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@RequestBody SellDto sellDto,
                                          @RequestHeader("access_token")String token){

        String email = tokenProvider.getEmailBytoken(token);
        Optional<Customer> seller = customerRepository.findByEmail(email);
        if(seller.isPresent()){
            Customer customer = seller.get();
            Long userId = customer.getUserId();
            productService.productRegister(sellDto, userId);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 user가 없습니다");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("물품 등록이 완료 되었습니다");
    }

    //모든 상품 정보 조회
    @GetMapping("/getAll")
    public List<SellDto> getAll(Pageable pageable){


        Page<Product> productPage = productService.findAll(pageable);
        List<Product> productEntity = productPage.getContent();

        return productEntity.stream()
                .map(product -> {
                    List<String> colorList = sellService.getProductColor(product.getProductId());
                    List<String> sizeList = sellService.getProductSize(product.getProductId());
                    Long userId = productService.findUserIdByProductId(product.getProductId());
                    return SellDto.fromEntity(product, colorList, sizeList, userId);
                })
                .collect(Collectors.toList());
    }



}

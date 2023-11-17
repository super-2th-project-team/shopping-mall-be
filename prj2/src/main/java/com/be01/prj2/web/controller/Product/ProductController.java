package com.be01.prj2.web.controller.Product;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.Customer;
import com.be01.prj2.entity.category.Category;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.repository.ProductRepository;
import com.be01.prj2.service.CustomerService;
import com.be01.prj2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;


    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@RequestBody SellDto sellDto,
                                          @RequestHeader("access_token")String token){

        String email = tokenProvider.getEmailBytoken(token);
        Customer seller = customerRepository.findCustomerByEmail(email);
        Long sellerId= seller.getUserId();
        productService.productRegister(sellDto, sellerId);

        return ResponseEntity.status(HttpStatus.CREATED).body("물품 등록이 완료 되었습니다");
    }
}

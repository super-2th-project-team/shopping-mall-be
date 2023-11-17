package com.be01.prj2.web.controller.Product;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.Customer;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.repository.ProductRepository;
import com.be01.prj2.service.CustomerService;
import com.be01.prj2.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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


    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@RequestBody SellDto sellDto,
                                          @RequestHeader("access_token")String token){

        String email = tokenProvider.getEmailBytoken(token);
        Optional<Customer> seller = customerRepository.findByEmail(email);
        if(seller.isPresent()){
            Customer customer = seller.get();
            Long userId = customer.getUserId();
            log.info("Seller ID :" + userId );
            productService.productRegister(sellDto, userId);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 user가 없습니다");
        }


        return ResponseEntity.status(HttpStatus.CREATED).body("물품 등록이 완료 되었습니다");
    }

    @GetMapping("/get/color_size")
    public ArrayList<?>  getColorSize(@RequestParam Long productId){
        ArrayList<?> colorList= productRepository.findByProductId(productId);
        return colorList;
    }
}

package com.be01.prj2.web.controller.SellService;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.ColorRepository;
import com.be01.prj2.repository.CustomerRepository;
import com.be01.prj2.repository.ProductRepository;
import com.be01.prj2.repository.SizeRepository;
import com.be01.prj2.service.ProductService.ProductService;
import com.be01.prj2.service.SellService.SellService;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/product")
public class SellController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final SellService sellService;
    private final CustomerService customerService;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;


    @GetMapping("/get/color")
    public Map<String,String> getColor(@RequestHeader("access_token") @RequestParam Long productId){
        List<String> productColor = sellService.getProductColor(productId);
        Map<String, String> response = new HashMap<>();
        response.put(productId.toString(), productColor.toString());
        return response;
    }

    @GetMapping("/get/size")
    public Map<String,String> getSize(@RequestHeader("access_token") @RequestParam Long productId){
        List<String> productSize = sellService.getProductSize(productId);
        Map<String, String> response = new HashMap<>();
        response.put(productId.toString(), productSize.toString());
        return response;
    }

    @GetMapping("/userSell")
    private List<SellDto> UserSellProduct(@RequestHeader("access_token") String token){
        String email = tokenProvider.getEmailBytoken(token);
        return customerRepository.findByEmail(email)
                .map(customer -> {
                    Long userId = customer.getUserId();
                    return sellService.findUserProduct(token, userId);
                })
                .orElse(Collections.emptyList());
    }


}

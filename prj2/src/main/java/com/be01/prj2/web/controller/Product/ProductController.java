package com.be01.prj2.web.controller.Product;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.service.S3Service.S3Service;
import com.be01.prj2.service.SellService.SellService;
import com.be01.prj2.service.customerService.CustomerService;
import com.be01.prj2.service.ProductService.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final S3Service s3Service;

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
        return ResponseEntity.status(HttpStatus.CREATED).body("물품 등록이 완료 되었습니다 물품 ID를 확인해 주세요");
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
                    List<String> imgList = sellService.getProductImg(product.getProductId());
                    return SellDto.fromEntity(product, colorList, sizeList, userId, imgList);
                })
                .collect(Collectors.toList());
    }

    //토큰을 받아 상품 재고 수정
    @PostMapping("/stockModify/{productId}")
    public ResponseEntity<String> updateStock(@RequestHeader("access_token") String token,
                                              @PathVariable Long productId,
                                              @RequestBody Map<String, Integer> requestBody) {
        Integer productStock = requestBody.get("productStock");
        try {
            productService.stockModify(token, productId, productStock);
            return ResponseEntity.status(HttpStatus.CREATED).body("물품 재고가 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 물품이 없습니다.");
        }
    }

    @PostMapping("/imgUpload")
    public ResponseEntity<List<String >> productImgsUpload(@RequestParam("id")Long productId,
                                            @RequestParam("files")List<MultipartFile> files) {
        List<String> uploadUrls = s3Service.uploadProductImg(productId, files);
        return ResponseEntity.ok().body(uploadUrls);

    }

    @PostMapping("/discount/{productId}")
    public ResponseEntity<String> discount(@RequestHeader("access_token") String token,
                                           @PathVariable Long productId,
                                           @RequestBody Map<String, Integer> requestBody){

        Integer discount = requestBody.get("discount");
        return  productService.discount(token,productId,discount);
    }
}

package com.be01.prj2.service.ProductService;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.EncryptedPutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;

import com.be01.prj2.entity.product.ProductImg;
import com.be01.prj2.exception.EmptyFileException;
import com.be01.prj2.exception.FileUploadFailedException;
import com.be01.prj2.jwt.TokenProvider;

import com.be01.prj2.repository.productRepository.ColorRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ImgRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.repository.productRepository.SizeRepository;
import com.be01.prj2.service.S3Service.CommonUtils;
import com.be01.prj2.service.S3Service.S3Service;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
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
            List<String> color = Arrays.asList("brown", "black", "beige", "white", "ivory", "navy", "gray");
            List<String> size = Arrays.asList("S", "M", "L", "XL", "FREE");

            Product product = Product.builder()
                    .productId(sellDto.getProductId())
                    .productName(sellDto.getProductName())
                    .productPrice(sellDto.getProductPrice())
                    .productInfo(sellDto.getProductInfo())
                    .productStock(sellDto.getProductStock())
                    .productSell(sellDto.getProductSell())
                    .productEnroll(sellDto.getProductEnroll())
                    .category(sellDto.getCategory())
                    .subCategory(sellDto.getSubCategory())
                    .color(color.subList(0, 7))
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

    //판매자가 팔고 있는 물품 재고 수정
    @Transactional
    public Product stockModify(@RequestHeader("access_token")String token, Long productId, Integer productStock) throws AccessDeniedException {
        String email = tokenProvider.getEmailBytoken(token);
        Optional<Customer> seller = customerRepository.findByEmail(email);

        if (seller.isPresent()) {
            Customer customer = seller.get();
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                if (product.getSellerId().equals(customer)) {
                    product.setProductStock(productStock);
                    return productRepository.save(product);
                } else {
                    throw new AccessDeniedException("해당 상품의 판매자가 아닙니다.");
                }
            } else {
                throw new EntityNotFoundException("상품을 찾을 수 없습니다.");
            }
        } else {
            throw new EntityNotFoundException("판매자를 찾을 수 없습니다.");
        }
    }





}

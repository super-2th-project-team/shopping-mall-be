package com.be01.prj2.service;

import com.be01.prj2.entity.ProductEntity;
import com.be01.prj2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




    public List<ProductEntity> getAllProducts(){
        List<ProductEntity> products = productRepository.findAll();

        if (products.isEmpty()){
            throw new ProductNotFoundExcepion("상품이 없습니다.");

        }
        return productRepository.findAll();
    }

    public <Product> List<Product> findAllProducts() {
    }
}
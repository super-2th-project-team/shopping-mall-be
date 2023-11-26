package com.be01.prj2.service.ProductService;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ColorRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.repository.productRepository.SizeRepository;
import com.be01.prj2.service.cartService.CartService;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.persistence.EntityNotFoundException;
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
    private final CartService cartService;


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
                    .productName(sellDto.getName())
                    .productPrice(sellDto.getPrice())
                    .originPrice(sellDto.getPrice())
                    .productInfo(sellDto.getDescription())
                    .productStock(sellDto.getStock())
                    .productSell(0)
                    .productEnroll(sellDto.getEnroll())
                    .category(sellDto.getCategory())
                    .subCategory(sellDto.getSubCategory())
                    .discount(0)
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

    @Transactional
    public ResponseEntity<String> discount(String token, Long productId, Integer discount){

        String email = tokenProvider.getEmailBytoken(token);
        Optional<Customer> isCustomer = customerRepository.findByEmail(email);

        if(isCustomer.isPresent()){
            Customer seller = isCustomer.get();
            Product product = productRepository.findByProductId(productId);
            if(product.getSellerId().equals(seller)){

                int originPrice = product.getOriginPrice();
                product.setProductPrice(originPrice);

                product.setDiscount(discount);
                double newPrice = originPrice - (originPrice * (discount * 0.01));
                product.setProductPrice((int)newPrice);
                productRepository.save(product);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("할인율이 적용되었습니다 현재 가격 :" + product.getProductPrice());

            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("판매자가 아니닙니다");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("상품이 없습니다");
        }

    }

    //productId로 상품 삭제
    @Transactional
    public void deleteProductAndUpdateCarts(@RequestHeader("access_token")String token, Long productId) throws AccessDeniedException {
        String email = tokenProvider.getEmailBytoken(token);
        Optional<Customer> seller = customerRepository.findByEmail(email);

        if (seller.isPresent()) {
            Customer customer = seller.get();
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                List<CartProduct> cartProducts = product.getCartProducts();

                if (product.getSellerId().equals(customer)) {
                    int removePrice = calculateRemovePrice(cartProducts);
                    int removeQuantity = calculateRemoveQuantity(cartProducts);

                    cartService.updateCartsOnProductDelete(product, removePrice, removeQuantity);

                    productRepository.delete(product);
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
    private int calculateRemovePrice(List<CartProduct> cartProducts){
        int totalRemovePrice = 0;
        for (CartProduct cartProduct : cartProducts) {
            int productPrice = cartProduct.getPrice();
            int cartQuantity = cartProduct.getCartQuantity();
            totalRemovePrice += productPrice * cartQuantity;
        }
        return totalRemovePrice;
    }
    private int calculateRemoveQuantity(List<CartProduct> cartProducts){
        int totalRemoveQuantity = 0;
        for (CartProduct cartProduct : cartProducts) {
            int cartQuantity = cartProduct.getCartQuantity();
            totalRemoveQuantity += cartQuantity;
        }
        return totalRemoveQuantity;
    }

}

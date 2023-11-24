package com.be01.prj2.service.SellService;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.productRepository.ColorRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ImgRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.repository.productRepository.SizeRepository;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellService {

    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ImgRepository imgRepository;


    //판매자가 팔고있는 상품의 ID에 해당하는 색상 가져오기
    @Transactional
    public List<String> getProductColor(Long productId){
        List<String> colorList = colorRepository.findDistinctColorsByProductId(productId);
        return colorList;
    }
    //판매자가 팔고있는 상품의 ID에 해당하는 사이즈 가져오기
    @Transactional
    public List<String > getProductSize(Long productId){
        List<String > sizeList = sizeRepository.findDistinctSizesByProductId(productId);
        return sizeList;
    }

    @Transactional
    public List<String > getProductImg(Long productId){
        List<String > imgList = imgRepository.findDistinctImgsByProductId(productId);
        return imgList;
    }

    @Transactional
    public List<SellDto> findUserProduct(@RequestHeader("access_token")String token ,Long userId){

        String email = tokenProvider.getEmailBytoken(token);
        Optional<Customer> seller = customerRepository.findByEmail(email);

        if(seller.isPresent()){
            Customer customer = seller.get();
            List<Product> userProducts = productRepository.findProductBySellerId(customer);
            return   userProducts.stream()
                    .map(product -> {
                        List<String> colorList = getProductColor(product.getProductId());
                        List<String> sizeList = getProductSize(product.getProductId());
                        List<String> imgList = getProductImg(product.getProductId());
                        return SellDto.fromEntity(product, colorList, sizeList, userId, imgList);
                    })
                    .collect(Collectors.toList());

        }
        return null;
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

package com.be01.prj2.service.SellService;

import com.be01.prj2.dto.productsDto.SellDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.productRepository.ColorRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.repository.productRepository.SizeRepository;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

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
                        return SellDto.fromEntity(product, colorList, sizeList, userId);
                    })
                    .collect(Collectors.toList());

        }
        return null;
    }

}

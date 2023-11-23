package com.be01.prj2.service.S3Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.EncryptedPutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.ProductImg;
import com.be01.prj2.exception.EmptyFileException;
import com.be01.prj2.exception.FileUploadFailedException;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;
    private final ImgRepository imgRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException("파일이 없습니다");
        }
    }

    //유저 프로필 업데이트
    public String uploadProfile(String email, MultipartFile multipartFile) throws FileUploadFailedException {
        validateFileExists(multipartFile);

        String fileName = CommonUtils.buildFileName(email, Objects.requireNonNull(multipartFile.getOriginalFilename()));

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(new EncryptedPutObjectRequest(bucketName, fileName, inputStream, objectMetadata));


        }catch (Exception e){
            log.error("Amazon S3 파일 업로드 실패: {}", e.getMessage(), e);
            throw new FileUploadFailedException("파일 업로드에 실패했습니다");
        }

        Optional<Customer> byEmail = customerRepository.findByEmail(email);
        if(byEmail.isPresent()){
            Customer customer = byEmail.get();
            customer.setProfileImg(amazonS3Client.getUrl(bucketName, fileName).toString());
            customerRepository.save(customer);
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }
    //물품 사진 다중 업로드
    public List<String> uploadProductImg(Long productId, List<MultipartFile> multipartFileList){

        List<String> filenameList = new ArrayList<>();

        multipartFileList.forEach(file -> {
            try {
                String filename = uploadProductImgSingle(productId, file);
                filenameList.add(filename);
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        return filenameList;
    }
    //물품 사진 다중 업로드
    public String uploadProductImgSingle(Long productId, MultipartFile multipartFile) throws FileUploadFailedException {
        validateFileExists(multipartFile);
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        String fileName = CommonUtils.buildProductName(productId, randomString + "_" + Objects.requireNonNull(multipartFile.getOriginalFilename()));

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(new EncryptedPutObjectRequest(bucketName, fileName, inputStream, objectMetadata));


        }catch (Exception e){
            log.error("Amazon S3 파일 업로드 실패: {}", e.getMessage(), e);
            throw new FileUploadFailedException("파일 업로드에 실패했습니다");
        }
        ProductImg productImg = ProductImg.builder()
                .productId(productId)
                .img(amazonS3Client.getUrl(bucketName, fileName).toString())
                .build();

        imgRepository.save(productImg);

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }




}

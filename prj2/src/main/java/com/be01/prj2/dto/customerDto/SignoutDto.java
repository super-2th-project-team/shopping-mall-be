package com.be01.prj2.dto.customerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignoutDto {

    private String email;
    private String password;
    private String mobile;
}

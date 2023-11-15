package com.be01.prj2.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TokenPair {

    private final String accessToken;
    private final String refreshToken;
}

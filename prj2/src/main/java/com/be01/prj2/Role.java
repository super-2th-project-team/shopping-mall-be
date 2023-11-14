package com.be01.prj2;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Role {
    @Schema(description = "유저")
    USER,
    @Schema(description = "관리자")
    ADMIN
}

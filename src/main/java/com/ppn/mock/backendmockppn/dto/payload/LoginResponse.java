package com.ppn.mock.backendmockppn.dto.payload;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType;
}

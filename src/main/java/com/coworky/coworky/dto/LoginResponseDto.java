package com.coworky.coworky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String loginId;
    private String role;
    private String accessToken;
    private String refreshToken;

}

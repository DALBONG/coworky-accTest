package com.coworky.coworky.dto;

import lombok.Data;

@Data
public class KakaoLoginResultDto {

    private String status;  // SUCCESS, NEED_LINK, ERROR

    // SUCCESS일 때만 사용
    private String loginId;
    private String role;
    private String accessToken;
    private String refreshToken;

    // NEED_LINK 일때 만 사용
    private Long kakaoId;

    // ERROR일 때만 사용
    private String message;

}

package com.coworky.coworky.dto;

import lombok.Data;

@Data
public class KakaoTokenResponseDto {

    private String access_token;                // 액세스 토큰 (카카오 API 호출할때 쓰는 인증 토큰)
    private String token_type;                  // 토큰 타입
    private String refresh_token;               // 리프레스 토큰
    private Integer expires_in;                 // 액세스 토큰 만료 시간
    private String refresh_token_expires_in;    // 리프레시 토큰 만료 시간
    private String scope;                       // 권한 범위 (어떤 정보 접근 권한을 얻었는지)


}

package com.coworky.coworky.service;

import com.coworky.coworky.config.KakaoConfig;
import com.coworky.coworky.dto.KakaoTokenResponseDto;
import com.coworky.coworky.dto.KakaoUserResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoApiClient {

    private final RestTemplate restTemplate;
    private final KakaoConfig kakaoConfig;

    // 카카오 토큰 요청
    public KakaoTokenResponseDto getKakaoToken(String code, String redirectUrl) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        // 요청 파라미터
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoConfig.getClientId());
        params.add("redirect_uri", redirectUrl);
        params.add("code", code);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> request = new HttpEntity<>(params, headers);

        System.out.println("=== 리다이렉트 uri === [ " + redirectUrl + " ]");

        return restTemplate.postForObject(tokenUrl, request, KakaoTokenResponseDto.class);

    }

    // 카카오 사용자 정보 요청
    public KakaoUserResponseDto geUserInfo(String accessToken){
        String UserInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserResponseDto> response =
                restTemplate.exchange(UserInfoUrl, HttpMethod.GET, request, KakaoUserResponseDto.class);
        return response.getBody();
    }



}

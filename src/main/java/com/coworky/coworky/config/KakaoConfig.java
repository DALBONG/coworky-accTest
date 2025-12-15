package com.coworky.coworky.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoConfig {
    // application.yml에 적어둔 kakao 설정값을
    // 자바 코드에서 읽어올 수 있게 해주는 설정 클래스
    // 카카오 로그인 설정값을 관리하고 제공하는 파일

    private String clientId;
    private String redirectUri;
    private String clientSecret;

}

package com.coworky.coworky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    // KakaoApiClient가 HTTP 요청을 하기 위한 통신객체(RestTemplate)를
    // 스프링 방식으로 생성 + 관리하는 설정파일

    @Bean
    public RestTemplate restTemplate() {
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setConnectionRequestTimeout(5000);  // 연결 요청 타임아웃
//        factory.setReadTimeout(5000);               // 응답 타임아웃

        return new RestTemplate();
    }

}

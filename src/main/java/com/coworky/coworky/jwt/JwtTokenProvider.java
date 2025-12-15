package com.coworky.coworky.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // JWT 비밀키 읽어오기
    @Value("${jwt.secret}")
    private String secretKey;

    // 만료시간
    private final long ACCESS_TOKEN_VALIDITY_TIME = 1000L * 60 * 30;    // 30분
    private final long REFRESH_TOKEN_VALIDITY_TIME = 1000L * 60 * 60 * 24 * 7; // 7일

    public String createAccessToken(Long userId, String role) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))      // JWT의 주인 (유저 식별)
                .claim("role", role)                  // payload에 role 추가
                .setIssuedAt(now)                       // 발급시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_TIME))    // 만료 날짜
                .signWith(SignatureAlgorithm.HS256, secretKey)  // HS256 알고리즘 + 비밀키
                .compact(); // 최종 문자열 형태로 압축


    }

    public String createRefreshToken(Long userId) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 나중에 Redis에 저장할 때 TTL로 넣기 위해서 만든 메서드
    public long getRefreshTokenExpireMs(){
        return REFRESH_TOKEN_VALIDITY_TIME;
    }

}

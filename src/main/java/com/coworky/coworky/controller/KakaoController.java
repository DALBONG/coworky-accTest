package com.coworky.coworky.controller;

import com.coworky.coworky.dto.KakaoLinkRequestDto;
import com.coworky.coworky.dto.KakaoLoginResultDto;
import com.coworky.coworky.dto.KakaoUserResponseDto;
import com.coworky.coworky.dto.LoginResponseDto;
import com.coworky.coworky.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/kakao")
    public KakaoLoginResultDto kakaoLogin(
            @RequestParam String code,
            @RequestParam("redirect_uri") String redirectUri){
        return kakaoLoginService.kakaoLogin(code, redirectUri);
    }

    @PostMapping("/kakao/link")
    public KakaoLoginResultDto linkKakao(@RequestBody KakaoLinkRequestDto request){
        return kakaoLoginService.linkKakaoAccount(
                request.getKakaoId(),
                request.getLoginId(),
                request.getRawPwd(),
                request.getCompanyId()
        );
    }

}

package com.coworky.coworky.controller;


import com.coworky.coworky.dto.LoginRequestDto;
import com.coworky.coworky.dto.LoginResponseDto;
import com.coworky.coworky.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/login/user")
    public LoginResponseDto login(@RequestBody LoginRequestDto request){
        return authService.login(
                request.getLoginId(),
                request.getRawPwd(),
                request.getCompany()
        );
    }

}

package com.coworky.coworky.dto;

import lombok.Data;

@Data
public class KakaoLinkRequestDto {

    private Long kakaoId;
    private String loginId;
    private String rawPwd;
    private Long companyId;

}

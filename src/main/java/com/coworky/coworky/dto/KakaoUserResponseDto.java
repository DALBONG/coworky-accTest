package com.coworky.coworky.dto;

import lombok.Data;

@Data
public class KakaoUserResponseDto {

    private Long id;
    private KakaoAccount kakao_account;

    @Data
    public static class KakaoAccount{
        private String email;
        private Profile profile;

        @Data
        public static class Profile{
            private String nickname;
            private String profile_image_url;
            private String thumbnail_image_url;
        }
    }

}

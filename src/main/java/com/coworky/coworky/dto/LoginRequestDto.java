package com.coworky.coworky.dto;

import com.coworky.coworky.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    private String loginId;
    private String rawPwd;
    private Long company;


}

package com.coworky.coworky.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccomSearchRecommendDto {

    private Integer minPrice;
    private Integer maxPrice;
    private String keyword;
    private Boolean enableScore = true;

    public AccomSearchFilterDto recommendFilter(){
        AccomSearchFilterDto accfilter = new AccomSearchFilterDto();

        // 가격 범위 확장 로직 적용
        if(minPrice != null){
            int expandMin = Math.max(0, (int) (minPrice * 0.9));
            BigDecimal expandedMin = BigDecimal.valueOf(expandMin);
            accfilter.setMinPrice(expandedMin);
        }
        if(maxPrice != null){
            int expandMax = Math.max(0, (int) (maxPrice * 1.1));
            BigDecimal expandedMax = BigDecimal.valueOf(expandMax);
            accfilter.setMaxPrice(expandedMax);
        }

        // 키워드 검색 로직 적용 (isBlack() : 빈 문자열/공백일 경우 true 반환)
        if(keyword != null && !keyword.isBlank()){
            accfilter.setName(keyword);
            accfilter.setDescription(keyword);
            accfilter.setDetailInfo(keyword);
            accfilter.setCity(keyword);
            accfilter.setAddress(keyword);
        }
        accfilter.setEnableScore(enableScore != null && enableScore);

        return accfilter;
    }
}

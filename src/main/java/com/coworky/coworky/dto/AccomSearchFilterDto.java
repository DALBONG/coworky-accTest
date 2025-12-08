package com.coworky.coworky.dto;

import com.coworky.coworky.domain.AccommodationTags;
import com.coworky.coworky.domain.AccommodationType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
// 검색 필터 DTO
public class AccomSearchFilterDto {
    //Accommodation
    private String name; // 숙소 이름 키워드
    private String description; // 숙소 소개 키워드
    private String detailInfo; // 상세정보 키워드

    private BigDecimal minPrice; // 최소 금액
    private BigDecimal maxPrice; // 최대 금액

    private Set<AccommodationTags> tags; // 편의 시설 태그
    private AccommodationType type; // 숙소 타입
    // Location
    private String region; // 지역
    private String city; // 도시
    private String address; // 주소 키워드

    private boolean enableScore; // 검색 점수 정렬여부

}

package com.coworky.coworky.domain;

import lombok.Getter;

@Getter
public enum AccommodationTags {
    WIFI("초고속 WIFI"),
    OFFICE("전용 업무 공간"),
    PRINTER("사무기기"),
    FITNESS("피트니스"),
    SPA("스파"),
    CAFETERIA("카페"),
    KITCHEN("주방시설");

    private String accommodationTags;

    private AccommodationTags(String accommodationTags){
        this.accommodationTags = accommodationTags;
    }

}

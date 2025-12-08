package com.coworky.coworky.domain;

import lombok.Getter;

@Getter
public enum AccommodationType {
    HOTEL("호텔"),
    PENSION("펜션"),
    RESORT("리조트"),
    STAY("민박");

    private String accommodationType;

    private AccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }
}

package com.coworky.coworky.util;

import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.domain.AccommodationType;
import com.coworky.coworky.domain.Location;

import java.math.BigDecimal;
import java.util.Set;

import static com.coworky.coworky.domain.AccommodationTags.*;

public class TestUtils {

    public static Accommodation createMockAccommodationA(){
        Location locA = Location.createLocation("대한민국",
                    "충청도", "대전", "대전 중구 대종로 480",
                    new BigDecimal("36.327680"), new BigDecimal("127.427366")
        );

        return Accommodation.createAccommodation(
                "대전 성심 워케이션 호텔", "성심당과 가까운 업무 친화적 호텔입니다.",
                new BigDecimal("60000"), new BigDecimal("60000"), new BigDecimal("80000"), 4,
                "성심당 도보 3분, 넓은 데스크",
                Set.of(WIFI, OFFICE, CAFETERIA),
                AccommodationType.HOTEL,
                locA
        );
    }

    public static Accommodation createMockAccommodationB(){
        Location locB = Location.createLocation("대한민국", "경기도", "가평", "경기도 가평군 가평제방길 16",
                new BigDecimal("37.359570"), new BigDecimal("127.105399")
        );

        return Accommodation.createAccommodation(
                "가평 자라섬 워케이션 캠핑", "낭만의 캠핑과 넓은 사무실, 탁트인 자연과 함께합니다.",
                new BigDecimal("30000"), new BigDecimal("30000"), new BigDecimal("40000"), 4,
                "탁트인 전망, 넓은 사무실, 넓은 돔형 텐트",
                Set.of(WIFI, OFFICE, KITCHEN, FITNESS),
                AccommodationType.STAY,
                locB
        );
    }
}

package com.coworky.coworky.InitDb;

import com.coworky.coworky.domain.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

import static com.coworky.coworky.domain.AccommodationTags.*;
import static com.coworky.coworky.domain.AccommodationTags.KITCHEN;
import static com.coworky.coworky.domain.AccommodationTags.PRINTER;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class AccommodationDb {

    private final AccommodationService accommodationService;

    @PostConstruct
    public void init(){
        accommodationService.dbAcc();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class AccommodationService{

        private final EntityManager em;

        public void dbAcc(){

            Location loc1 = Location.createLocation(
                    "대한민국", "제주", "서귀포", "제주 서귀포시 칠십리로 87",
                    new BigDecimal("33.22891"), new BigDecimal("126.51542")
            );
            em.persist(loc1);

            Location loc2 = Location.createLocation(
                    "대한민국", "강원도", "강릉", "강원도 강릉시 창해로 307",
                    new BigDecimal("37.78932"), new BigDecimal("128.92603")
            );
            em.persist(loc2);

            Location loc3 = Location.createLocation(
                    "대한민국", "부산", "해운대구", "부산 해운대구 해운대해변로 292",
                    new BigDecimal("35.15953"), new BigDecimal("129.13162")
            );
            em.persist(loc3);

            Location loc4 = Location.createLocation(
                    "대한민국", "전라남도", "여수", "전남 여수시 오동도로 111",
                    new BigDecimal("34.75682"), new BigDecimal("127.75231")
            );
            em.persist(loc4);

            Location loc5 = Location.createLocation(
                    "대한민국", "충청남도", "태안", "충남 태안군 안면읍 꽃지해안로 204",
                    new BigDecimal("36.51032"), new BigDecimal("126.27672")
            );
            em.persist(loc5);

            Accommodation acc1 = Accommodation.createAccommodation(
                    "제주 바다뷰 호텔",
                    "전 객실 바다 전망을 제공하는 프리미엄 호텔입니다.",
                    new BigDecimal("70000"), new BigDecimal("70000"), new BigDecimal("90000"),6,
                    "바다 전망 객실, 조식 제공",
                    Set.of(WIFI, OFFICE),
                    AccommodationType.HOTEL,
                    loc1
            );
            em.persist(acc1);

            Accommodation acc2 = Accommodation.createAccommodation(
                    "강릉 워케이션 스테이", "해변과 가까운 조용한 업무 친화형 스테이입니다.",
                    new BigDecimal("60000"), new BigDecimal("60000"), new BigDecimal("80000"), 4,
                    "조용한 업무 공간, 공용 라운지, 무료 와이파이, 근처 카페 밀집 지역",
                    Set.of(WIFI, SPA, KITCHEN),
                    AccommodationType.STAY,
                    loc2
            );
            em.persist(acc2);

            Accommodation acc3 = Accommodation.createAccommodation(
                    "해운대 비즈니스 호텔", "비즈니스 여행객을 위한 해운대 중심 비즈니스 호텔입니다.",
                    new BigDecimal("65000"), new BigDecimal("65000"), new BigDecimal("85000"), 2,
                    "24시간 라운지, 업무용 데스크 완비, 바다 산책로 도보 3분, 초고속 와이파이",
                    Set.of(WIFI, OFFICE, PRINTER, CAFETERIA),
                    AccommodationType.HOTEL,
                    loc3
            );
            em.persist(acc3);

            Accommodation acc4 = Accommodation.createAccommodation(
                    "여수 오션 워크 펜션", "바다 전망이 좋은 워케이션 맞춤형 펜션입니다.",
                     new BigDecimal("55000"), new BigDecimal("55000"), new BigDecimal("75000"), 5,
                    "개별 테라스, 와이파이, 공용 업무 공간, 근처 오동도 관광지 접근 용이",
                    Set.of(WIFI, SPA, KITCHEN),
                    AccommodationType.PENSION,
                    loc4
            );
            em.persist(acc4);

            Accommodation acc5 = Accommodation.createAccommodation(
                    "태안 해변 리트릿 리조트","자연 속에서 집중 업무가 가능한 워케이션 리조트입니다.",
                     new BigDecimal("58000"), new BigDecimal("58000"), new BigDecimal("78000"), 4,
                    "넓은 업무 라운지, 산책로 접근성 우수, 전 객실 책상 및 조용한 환경",
                    Set.of(WIFI, OFFICE, CAFETERIA, FITNESS),
                    AccommodationType.RESORT,
                    loc5
            );
            em.persist(acc5);
        }
    }
}

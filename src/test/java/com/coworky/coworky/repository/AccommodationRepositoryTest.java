package com.coworky.coworky.repository;

import com.coworky.coworky.InitDb.AccommodationDb;
import com.coworky.coworky.config.TestQueryDslConfig;
import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.domain.AccommodationTags;
import com.coworky.coworky.domain.AccommodationType;
import com.coworky.coworky.domain.Location;
import com.coworky.coworky.dto.AccomSearchFilterDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.coworky.coworky.domain.AccommodationTags.*;
import static com.coworky.coworky.domain.AccommodationTags.CAFETERIA;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        excludeFilters = @ComponentScan.Filter(type =
            FilterType.ASSIGNABLE_TYPE,
            classes = AccommodationDb.class)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.devtools.restart.enabled=false",
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.jpa.show-sql=true"
})
@Import({AccommodationRepositoryImpl.class,
         TestQueryDslConfig.class})
@Transactional
class AccommodationRepositoryTest {

    @Autowired
    AccommodationRepository accRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void insertMockData(){
        Location locA = Location.createLocation("대한민국", "충청도", "대전", "대전 중구 대종로 480",
                new BigDecimal("36.327680"), new BigDecimal("127.427366")
        );
        em.persist(locA);

        Accommodation accA = Accommodation.createAccommodation(
                "대전 성심 워케이션 호텔", "성심당과 가까운 업무 친화적 호텔입니다.",
                new BigDecimal("60000"), new BigDecimal("60000"), new BigDecimal("80000"), 4,
                "성심당 도보 3분, 넓은 데스크",
                Set.of(WIFI, OFFICE, CAFETERIA),
                AccommodationType.HOTEL,
                locA
        );
        accRepository.save(accA);

        Location locB = Location.createLocation("대한민국", "경기도", "가평", "경기도 가평군 가평제방길 16",
                new BigDecimal("37.359570"), new BigDecimal("127.105399")
        );
        em.persist(locB);

        Accommodation accB = Accommodation.createAccommodation(
                "가평 자라섬 워케이션 캠핑", "낭만의 캠핑과 넓은 사무실, 탁트인 자연과 함께합니다.",
                new BigDecimal("30000"), new BigDecimal("30000"), new BigDecimal("40000"), 4,
                "탁트인 전망, 넓은 사무실, 넓은 돔형 텐트",
                Set.of(WIFI, OFFICE, KITCHEN, FITNESS),
                AccommodationType.STAY,
                locB
        );
        accRepository.save(accB);

        em.flush();
        em.clear();
    }

    @Test
    public void 가격_필터링테스트() throws Exception {
        //given
        AccomSearchFilterDto filter = new AccomSearchFilterDto();

        filter.setMinPrice(new BigDecimal("30000"));
        filter.setMaxPrice(new BigDecimal("50000"));

        List<Accommodation> result = accRepository.search(filter);
        
        //then
        assertThat(result.size()).isEqualTo(1);
        //assertThat(result.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");
        assertThat(result.get(0).getBasePrice())
                .isEqualByComparingTo(new BigDecimal("30000"));
    }
    
    @Test
    public void 지역_필터링테스트() throws Exception {
        //given
        AccomSearchFilterDto filter = new AccomSearchFilterDto();
        filter.setRegion("경기도");
        
        //when
        List<Accommodation> result = accRepository.search(filter);
    
        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName())
                .isEqualTo("가평 자라섬 워케이션 캠핑");
        assertThat(result.get(0).getBasePrice())
                .isEqualByComparingTo(new BigDecimal("30000"));
        assertThat(result.get(0).getLocation().getRegion())
                .isEqualTo("경기도");

        filter.setRegion("제주");
        List<Accommodation> noResult = accRepository.search(filter);
        assertThat(noResult).isEmpty();
    }
    
    @Test
    public void 키워드검색_필터링테스트() throws Exception {
        //given
        // 1. 이름 키워드
        AccomSearchFilterDto nameFilter = new AccomSearchFilterDto();
        nameFilter.setName("자라");

        // 2. 숙소 소개 키워드
        AccomSearchFilterDto descriptFilter = new AccomSearchFilterDto();
        descriptFilter.setDescription("낭만");

        // 3. 숙소 상세소개 키워드
        AccomSearchFilterDto detailFilter = new AccomSearchFilterDto();
        detailFilter.setDetailInfo("탁트인");

        // 4-1. 도시 검색 키워드 (가평)
        AccomSearchFilterDto cityFilterA = new AccomSearchFilterDto();
        cityFilterA.setCity("가평");

        // 5-1. 주소 검색 키워드 (가평)
        AccomSearchFilterDto addressFilterA = new AccomSearchFilterDto();
        addressFilterA.setAddress("제방길");

        // 4-2. 도시 검색 키워드 (대전)
        AccomSearchFilterDto cityFilterB = new AccomSearchFilterDto();
        cityFilterB.setCity("대전");

        // 5-2. 주소 검색 키워드 (대전)
        AccomSearchFilterDto addressFilterB = new AccomSearchFilterDto();
        addressFilterB.setAddress("대종로");

        // when+then
        // 1. 이름 키워드 검증
        List<Accommodation> nameResult = accRepository.search(nameFilter);
        assertThat(nameResult).hasSize(1);
        assertThat(nameResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 2.숙소 소개 키워드 검증
        List<Accommodation> descriptResult = accRepository.search(descriptFilter);
        assertThat(descriptResult).hasSize(1);
        assertThat(descriptResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 3. 숙소 상세소개 키워드 검증
        List<Accommodation> detailResult = accRepository.search(detailFilter);
        assertThat(detailResult).hasSize(1);
        assertThat(detailResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 4-1. 도시 키워드 검증 (가평)
        List<Accommodation> cityResultA = accRepository.search(cityFilterA);
        assertThat(cityResultA).hasSize(1);
        assertThat(cityResultA.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 5-1. 주소 키워드 검증 (가평)
        List<Accommodation> addressResultA = accRepository.search(addressFilterA);
        assertThat(addressResultA).hasSize(1);
        assertThat(addressResultA.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 4-2. 도시 키워드 검증 (대전)
        List<Accommodation> cityResultB = accRepository.search(cityFilterB);
        assertThat(cityResultB).hasSize(1);
        assertThat(cityResultB.get(0).getName()).isEqualTo("대전 성심 워케이션 호텔");

        // 5-2. 주소 키워드 검증 (대전)
        List<Accommodation> addressResultB = accRepository.search(addressFilterB);
        assertThat(addressResultB).hasSize(1);
        assertThat(addressResultB.get(0).getName()).isEqualTo("대전 성심 워케이션 호텔");

        // 6. 없는 키워드
        AccomSearchFilterDto noneFilter = new AccomSearchFilterDto();
        noneFilter.setName("없는 키워드");
        List<Accommodation> noneResult = accRepository.search(noneFilter);
        assertThat(noneResult).isEmpty();
    }
    
    @Test
    public void 태그_필터링테스트() throws Exception {
        //given
        // 1. 단일 태그
        AccomSearchFilterDto oneTagFilter = new AccomSearchFilterDto();
        oneTagFilter.setTags(Set.of(KITCHEN));

        // 2. 다중 태그
        AccomSearchFilterDto multiTagFilter = new AccomSearchFilterDto();
        multiTagFilter.setTags(Set.of(FITNESS, KITCHEN));

        // 3. 혼합 태그
        AccomSearchFilterDto mixTagFilter = new AccomSearchFilterDto();
        mixTagFilter.setTags(Set.of(SPA, FITNESS));

        // 4. X포함 태그
        AccomSearchFilterDto noneTagFilter = new AccomSearchFilterDto();
        noneTagFilter.setTags(Set.of(SPA));
        
        //when + then
        // 1. 단일태그 검증
        List<Accommodation> oneTagResult = accRepository.search(oneTagFilter);
        assertThat(oneTagResult).hasSize(1);
        assertThat(oneTagResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 2. 다중 태그 검증
        List<Accommodation> multiTagResult = accRepository.search(multiTagFilter);
        assertThat(multiTagResult).hasSize(1);
        assertThat(multiTagResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 3. 혼합 태그 검증
        List<Accommodation> mixTagResult = accRepository.search(mixTagFilter);
        assertThat(mixTagResult).hasSize(1);
        assertThat(mixTagResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 4. X포함 태그 검증
        List<Accommodation> noneTagResult = accRepository.search(noneTagFilter);
        assertThat(noneTagResult).isEmpty();
    }

    @Test
    public void 확장가격_테스트() throws Exception {
        //give
        // 1.숙소 가격 포함 base : 30000
        AccomSearchFilterDto includeFilter = new AccomSearchFilterDto();
        includeFilter.setMinPrice(new BigDecimal("27000"));
        includeFilter.setMaxPrice(new BigDecimal("44000"));

        // 2.숙소 가격 포함X
        AccomSearchFilterDto excludeFilter = new AccomSearchFilterDto();
        excludeFilter.setMinPrice(new BigDecimal("30001"));
        excludeFilter.setMaxPrice(new BigDecimal("29999"));

        // 3. 경계값 분석.
        // -> 실제 로직은 Service에서 처리됨. 레파 단에선 DTO가 가진 값으로
        //    minP <= baseP / maxP >= baseP (경계값만 테스트)
        // 3-1. 경계값
        AccomSearchFilterDto boundFilter = new AccomSearchFilterDto();
        boundFilter.setMinPrice(new BigDecimal("30000"));
        boundFilter.setMaxPrice(new BigDecimal("30000"));

        // 3-2. 경계값 성공 (baseP < maxP / baseP > minP )
        AccomSearchFilterDto boundSucessMin = new AccomSearchFilterDto();
        boundSucessMin.setMinPrice(new BigDecimal("29999"));

        AccomSearchFilterDto boundSucessMax = new AccomSearchFilterDto();
        boundSucessMax.setMaxPrice(new BigDecimal("30001"));

        // 3-3. 경계값 실패 (baseP < minP / baseP > mixP )
        AccomSearchFilterDto boundfailMin = new AccomSearchFilterDto();
        boundfailMin.setMinPrice(new BigDecimal("30001"));

        AccomSearchFilterDto boundfailMax = new AccomSearchFilterDto();
        boundfailMax.setMaxPrice(new BigDecimal("29999"));

        //when + then
        // 1. 포함 범위 검증
        List<Accommodation> includeResult = accRepository.search(includeFilter);
        assertThat(includeResult).hasSize(1);
        assertThat(includeResult.get(0).getName())
                .isEqualTo("가평 자라섬 워케이션 캠핑");

        // 2. 포함x 범위 검증
        List<Accommodation> excludeResult = accRepository.search(excludeFilter);
        assertThat(excludeResult).isEmpty();

        // 3-1. 경계값 테스트
        List<Accommodation> boundResult = accRepository.search(boundFilter);
        assertThat(boundResult).hasSize(1);
        assertThat(boundResult.get(0).getBasePrice())
                .isEqualByComparingTo(new BigDecimal("30000"));

        // 3-2. 경계값 성공 테스트
        assertThat(accRepository.search(boundSucessMin)).hasSize(2);

        List<Accommodation> sucessMaxResult = accRepository.search(boundSucessMax);
        assertThat(sucessMaxResult).hasSize(1);
        assertThat(sucessMaxResult.get(0).getBasePrice())
                .isEqualByComparingTo(new BigDecimal("30000"));

        // 3-3. 경계값 실패 테스트
        List<Accommodation> failMinResult = accRepository.search(boundfailMin);
        assertThat(failMinResult).hasSize(1);
        assertThat(failMinResult.get(0).getBasePrice())
                .isEqualByComparingTo(new BigDecimal("60000"));

        assertThat(accRepository.search(boundfailMax)).isEmpty();
    }
    
    @Test
    public void 스코어링_테스트() throws Exception {
        //given
        // 1. 스코어 정렬 (가평 -> 대전)
        AccomSearchFilterDto scoreFilter = new AccomSearchFilterDto();
        scoreFilter.setEnableScore(true);

        // 2. 기본 정렬 (대전 -> 가평)
        AccomSearchFilterDto baseFilter = new AccomSearchFilterDto();
        baseFilter.setEnableScore(false);
        
        //when
        List<Accommodation> scoreResult = accRepository.search(scoreFilter);
        List<Accommodation> baseResult = accRepository.search(baseFilter);

        //then
        // 1. 스코어 정렬 검증
        assertThat(scoreResult).hasSize(2);
        assertThat(scoreResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");
        assertThat(scoreResult.get(1).getName()).isEqualTo("대전 성심 워케이션 호텔");

        // 2. 기본 정렬 검증
        assertThat(baseResult).hasSize(2);
        assertThat(baseResult.get(0).getName()).isEqualTo("대전 성심 워케이션 호텔");
        assertThat(baseResult.get(1).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

    }
    
    @Test
    @DisplayName("필터 조합 테스트")
    public void 필터조합_테스트() throws Exception {
        // 1. 가격 + 지역 필터링
        AccomSearchFilterDto priceRegionFilter = new AccomSearchFilterDto();
        priceRegionFilter.setMinPrice(new BigDecimal("25000"));
        priceRegionFilter.setMaxPrice(new BigDecimal("70000"));
        priceRegionFilter.setRegion("충청도");

        List<Accommodation> priceRegionResult = accRepository.search(priceRegionFilter);

        assertThat(priceRegionResult).hasSize(1);
        assertThat(priceRegionResult.get(0).getName())
                .isEqualTo("대전 성심 워케이션 호텔");

        // 2. 키워드 + 태그 필터링
        AccomSearchFilterDto keywordTagFilter = new AccomSearchFilterDto();
        keywordTagFilter.setName("워케이션");
        keywordTagFilter.setTags(Set.of(KITCHEN, FITNESS));

        List<Accommodation> keywordTagResult = accRepository.search(keywordTagFilter);

        assertThat(keywordTagResult).hasSize(1);
        assertThat(keywordTagResult.get(0).getName())
                .isEqualTo("가평 자라섬 워케이션 캠핑");

        // 3. 지역 + 태그 실패
        AccomSearchFilterDto regionTagFail = new AccomSearchFilterDto();
        regionTagFail.setRegion("경기도");
        regionTagFail.setTags(Set.of(SPA));

        List<Accommodation> regionTagResult = accRepository.search(regionTagFail);

        assertThat(regionTagResult).isEmpty();

        // 4. 전체 혼합 (금액, 지역, 키워드, 태그)
        AccomSearchFilterDto allFilter = new AccomSearchFilterDto();
        allFilter.setMinPrice(new BigDecimal("25000"));
        allFilter.setMaxPrice(new BigDecimal("90000"));
        allFilter.setRegion("경기도");
        allFilter.setName("자라");
        allFilter.setTags(Set.of(KITCHEN, OFFICE));

        List<Accommodation> allResult = accRepository.search(allFilter);

        assertThat(allResult).hasSize(1);
        assertThat(allResult.get(0).getName())
                .isEqualTo("가평 자라섬 워케이션 캠핑");
    }

}
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.coworky.coworky.domain.AccommodationTags.*;
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
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=true"
})
@Import({AccommodationRepositoryImpl.class,
         TestQueryDslConfig.class})
@Transactional
@Rollback(false)
class AccommodationRepositoryTest {

    @Autowired
    AccommodationRepository accRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void insertMockData(){
        Location testLoc = Location.createLocation("대한민국", "경기도", "가평", "경기도 가평군 가평제방길 16",
                new BigDecimal("37.359570"), new BigDecimal("127.105399")
        );
        em.persist(testLoc);

        Accommodation testAcc = Accommodation.createAccommodation(
                "가평 자라섬 워케이션 캠핑", "낭만의 캠핑과 넓은 사무실, 탁트인 자연과 함께합니다.",
                new BigDecimal("30000"), new BigDecimal("30000"), new BigDecimal("40000"), 4,
                "탁 트인 전망, 넓은 사무실, 넓은 돔형 텐트",
                Set.of(WIFI, OFFICE, KITCHEN),
                AccommodationType.STAY,
                testLoc
        );
        accRepository.save(testAcc);
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
        assertThat(result.get(0).getBasePrice()).isEqualTo("30000");
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
        assertThat(result.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");
        assertThat(result.get(0).getBasePrice()).isEqualTo("30000");
        assertThat(result.get(0).getLocation().getRegion()).isEqualTo("경기도");

        filter.setRegion("제주");
        List<Accommodation> noResult = accRepository.search(filter);
        assertThat(noResult).isEmpty();
    }
    
    @Test
    public void 키워드검색_필터링테스트() throws Exception {
        //given
        // 1. 이름 키워드
        AccomSearchFilterDto nameFilter = new AccomSearchFilterDto();
        nameFilter.setName(" 자라");
        // 2. 숙소 소개 키워드
        AccomSearchFilterDto descriptFilter = new AccomSearchFilterDto();
        descriptFilter.setDescription("낭만");
        // 3. 숙소 상세소개 키워드
        AccomSearchFilterDto detailFilter = new AccomSearchFilterDto();
        detailFilter.setDetailInfo("탁 트인");
        // 4. 도시 검색 키워드
        AccomSearchFilterDto cityFilter = new AccomSearchFilterDto();
        cityFilter.setCity("가평");
        // 5. 주소 검색 키워드
        AccomSearchFilterDto addressFilter = new AccomSearchFilterDto();
        addressFilter.setAddress("제방길");

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

        // 4. 도시 키워드 검증
        List<Accommodation> cityResult = accRepository.search(cityFilter);
        assertThat(cityResult).hasSize(1);
        assertThat(cityResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

        // 5. 주소 키워드 검증
        List<Accommodation> addressResult = accRepository.search(addressFilter);
        assertThat(addressResult).hasSize(1);
        assertThat(addressResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

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
        oneTagFilter.setTags(Set.of(WIFI));

        // 2. 다중 태그
        AccomSearchFilterDto multiTagFilter = new AccomSearchFilterDto();
        multiTagFilter.setTags(Set.of(WIFI, KITCHEN));

        // 3. 혼합 태그
        AccomSearchFilterDto mixTagFilter = new AccomSearchFilterDto();
        mixTagFilter.setTags(Set.of(WIFI, FITNESS));

        // 4. X포함 태그
        AccomSearchFilterDto noneTagFilter = new AccomSearchFilterDto();
        noneTagFilter.setTags(Set.of(CAFETERIA));
        
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
    public void 확장가격_범위테스트() throws Exception {
        //give
        // 1.숙소 가격 포함 base : 30000
        AccomSearchFilterDto includeFilter = new AccomSearchFilterDto();
        includeFilter.setMinPrice(new BigDecimal("27000"));
        includeFilter.setMaxPrice(new BigDecimal("44000"));

        //when + then
        // 1. 포함 범위 검증
        List<Accommodation> includeResult = accRepository.search(includeFilter);
        assertThat(includeResult).hasSize(1);
        assertThat(includeResult.get(0).getName()).isEqualTo("가평 자라섬 워케이션 캠핑");

    }
}
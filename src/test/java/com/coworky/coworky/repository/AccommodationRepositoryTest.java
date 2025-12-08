package com.coworky.coworky.repository;

import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.domain.AccommodationType;
import com.coworky.coworky.domain.Location;
import com.coworky.coworky.dto.AccomSearchFilterDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.coworky.coworky.domain.AccommodationTags.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AccommodationRepositoryImpl.class})
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.jpa.show-sql=true"
})
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
                "가평 자라섬 워케이션 캠핑", "낭만의 캠핑과 넓은 사무실, 탁 트인 자연과 함께합니다.",
                new BigDecimal("30000"), new BigDecimal("30000"), new BigDecimal("40000"), 4,
                "탁트인 전망, 넓은 사무실, 넓은 돔형 텐트",
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
        filter.setMinPrice(new BigDecimal("20000"));
        filter.setMaxPrice(new BigDecimal("50000"));

        List<Accommodation> result = accRepository.search(filter);
        
        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("가평");
        
        
    }
}
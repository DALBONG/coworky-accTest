package com.coworky.coworky.service;

import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.dto.AccomSearchFilterDto;
import com.coworky.coworky.repository.AccommodationRepository;
import com.coworky.coworky.util.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    @Mock
    AccommodationRepository accRepository;

    @InjectMocks
    AccommodationService accService;

    @Test
    @DisplayName("1. 검색 성공시 엔티티 -> DTO 변환")
    public void 검색성공시_DTO반환() throws Exception {
        //given
        AccomSearchFilterDto searchFilter = new AccomSearchFilterDto();
        Accommodation accA = TestUtils.createMockAccommodationA();
        Accommodation accB = TestUtils.createMockAccommodationB();

        List<Accommodation> mockEntity = List.of(accA, accB);

        // Repository의 search메서드를 mockEntity를 반환하도록 설정.
        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willReturn(mockEntity);

        // t+when
        List<Accommodation> searchResult = accService.search(searchFilter);

        // 1. 레포가 정확히 한번 호출 되었나? 검증
        verify(accRepository).search(searchFilter);

        // 2. 반환 결과 2개 검증
        assertThat(searchResult).hasSize(2);

        // 3. DTO 변환 확인 검증
        assertThat(searchResult.get(0).getName())
                .isEqualTo(accA.getName());
        assertThat(searchResult.get(1).getBasePrice())
                .isEqualByComparingTo(accB.getBasePrice());

        System.out.println("--- 테스트 결과 DTO 내용 ---");
        System.out.println("1번 숙소 이름 (기대값: " + accA.getName() + ") = " + searchResult.get(0).getName());
        System.out.println("2번 숙소 가격 (기대값: " + accB.getBasePrice() + ") = " + searchResult.get(1).getBasePrice());
        System.out.println("---------------------------");
    }

    @Test
    @DisplayName("2. 검색결과 없을때, 빈 리스트 반환")
    public void 검색결과_없음() throws Exception {
        //given
        AccomSearchFilterDto searchFilter = new AccomSearchFilterDto();

        // 빈 리스트 생성.
        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willReturn(List.of());
        
        //when
        List<Accommodation> searchResult = accService.search(searchFilter);
    
        //then
        verify(accRepository).search(searchFilter);
        assertThat(searchResult).isEmpty();

        if (searchResult.isEmpty()){
            System.out.println("빈 리스트 입니다!");
        } else {
            System.out.println("빈 리스트가 아닙니다!");
        }
    }

    @Test
    @DisplayName("3. 가격 확장 추천 (min -10% ~ max +10%)")
    public void 가격확장_추천테스트() throws Exception {
        //given
        AccomSearchFilterDto originPrice = new AccomSearchFilterDto();

        originPrice.setMinPrice(new BigDecimal("20000")); //-> 18000
        originPrice.setMaxPrice(new BigDecimal("40000")); //-> 44000

        AccomSearchFilterDto recommendPrice = new AccomSearchFilterDto();

        //given
        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willAnswer(invocation -> {
                    AccomSearchFilterDto dtoFilter = invocation.getArgument(0);

                    // then (내부에서 검증 로직 실행)
                    assertThat(dtoFilter.getMinPrice())
                            .isEqualByComparingTo("18000");
                    assertThat(dtoFilter.getMaxPrice())
                            .isEqualByComparingTo("44000");

                    return List.of();
                });
        //when
        accService.recommend(originPrice);

        //then
        verify(accRepository).search(any(AccomSearchFilterDto.class));
    }

    @Test
    @DisplayName("4. 가격추천 0미만 방지, 10% 계산 검증")
    public void 가격추천_0미만X() throws Exception {
        //given
        AccomSearchFilterDto originPrice = new AccomSearchFilterDto();
        originPrice.setMinPrice(new BigDecimal("10"));
        originPrice.setMaxPrice(new BigDecimal("10000"));

        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willAnswer(invocation -> {
                   AccomSearchFilterDto dtoFilter = invocation.getArgument(0);

                   // - 10% 0미만 되기 어려움 -> 결과가 0이상인지 확인만.
                   assertThat(dtoFilter.getMinPrice()
                           .compareTo(BigDecimal.ZERO)).isNotNegative();

                   assertThat(dtoFilter.getMinPrice())
                           .isEqualByComparingTo(new BigDecimal("9.0"));

                   return List.of();
                });
        //when
        accService.recommend(originPrice);

        //then
        verify(accRepository).search(any(AccomSearchFilterDto.class));
    }

    @Test
    @DisplayName("5. 필터 결합")
    public void 필터결합_테스트() throws Exception {
        //given
        AccomSearchFilterDto originFilter = new AccomSearchFilterDto();

        originFilter.setRegion("경기도"); // 지역 선택
        originFilter.setMinPrice(new BigDecimal("20000")); // 최소금액
        originFilter.setMaxPrice(new BigDecimal("80000")); // 최대 금액
        originFilter.setDetailInfo("넓은"); // 키워드 검색

        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willAnswer(invocation -> {
                    AccomSearchFilterDto finalFilter = invocation.getArgument(0);

                    //then
                    assertThat(finalFilter.getRegion()).isEqualTo("경기도");
                    assertThat(finalFilter.getMinPrice())
                            .isEqualByComparingTo(new BigDecimal("18000"));
                    assertThat(finalFilter.getDetailInfo()).isEqualTo("넓은");

                    return List.of();
                });
        //when
        accService.recommend(originFilter);

        //then
        verify(accRepository).search(any(AccomSearchFilterDto.class));
    }
    
    @Test
    @DisplayName("6. 스코어링 정렬")
    public void 스코어링_정렬테스트() throws Exception {
        // enableScore = true
        //given
        AccomSearchFilterDto tEnableFilter = new AccomSearchFilterDto();
        tEnableFilter.setEnableScore(true);

        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willAnswer(invocation -> {
                   AccomSearchFilterDto scoreFilter = invocation.getArgument(0);

                   //then
                    assertThat(scoreFilter.isEnableScore()).isTrue();

                    return List.of();
                });
        //when
        accService.recommend(tEnableFilter);
        //then
        verify(accRepository).search(any(AccomSearchFilterDto.class));

        // enableScore = false
        //given
        AccomSearchFilterDto fEnableFilter = new AccomSearchFilterDto();
        fEnableFilter.setEnableScore(false);

        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willAnswer(invocation -> {
                    AccomSearchFilterDto scoreFilter = invocation.getArgument(0);

                    //then
                    assertThat(scoreFilter.isEnableScore()).isFalse();

                    return List.of();
                });
        //when
        accService.recommend(fEnableFilter);

        verify(accRepository, times(2)).search(any(AccomSearchFilterDto.class));

    }
    
    @Test
    @DisplayName("7. 스코어링 정렬 결과 출력")
    public void 정렬결과_테스트() throws Exception {
        Accommodation accDj = TestUtils.createMockAccommodationA();
        Accommodation accGp = TestUtils.createMockAccommodationB();

        List<Accommodation> nomalOrder = List.of(accDj, accGp);
        List<Accommodation> scoreOrder = List.of(accGp, accDj);

        // 1. 스코어 정렬 (enableScore = ture)
        //given
        AccomSearchFilterDto scoreFilter = new AccomSearchFilterDto();
        scoreFilter.setEnableScore(true);

        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willReturn(scoreOrder);
        //when
        List<Accommodation> scoreResult = accRepository.search(scoreFilter);
    
        //then
        assertThat(scoreResult).hasSize(2);
        assertThat(scoreResult.get(0).getName()).isEqualTo(accGp.getName());
        assertThat(scoreResult.get(1).getName()).isEqualTo(accDj.getName());

        System.out.println("----------- 스코어 정렬 -----------");
        System.out.println("스코어링1순 (가평) : " + scoreResult.get(0).getName());
        System.out.println("스코어링2순 (대전) : " + scoreResult.get(1).getName());

        // 2. 기본 정렬 (enableScore = false)
        //given
        AccomSearchFilterDto nomalFilter = new AccomSearchFilterDto();
        nomalFilter.setEnableScore(false);

        given(accRepository.search(any(AccomSearchFilterDto.class)))
                .willReturn(nomalOrder);
        //when
        List<Accommodation> nomalResult = accRepository.search(nomalFilter);

        //then
        assertThat(nomalResult).hasSize(2);
        assertThat(nomalResult.get(0).getName()).isEqualTo(accDj.getName());
        assertThat(nomalResult.get(1).getName()).isEqualTo(accGp.getName());

        System.out.println("----------- 기본 정렬 -----------");
        System.out.println("기본정렬1순 (대전) : " + nomalResult.get(0).getName());
        System.out.println("기본정렬2순 (대전) : " + nomalResult.get(1).getName());

        verify(accRepository, times(2)).search(any(AccomSearchFilterDto.class));
    }
}
package com.coworky.coworky.controller;

import com.coworky.coworky.config.TestCacheConfig;
import com.coworky.coworky.config.TestQueryDslConfig;
import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.dto.AccomSearchFilterDto;
import com.coworky.coworky.dto.AccommodationDto;
import com.coworky.coworky.service.AccommodationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccommodationController.class)
@Import(TestCacheConfig.class)
@EnableCaching
class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccommodationService accService;

    @Autowired
    private ObjectMapper objMapper;

    private List<AccommodationDto> mockDtoList;

    @BeforeEach
    void setUp() {
        AccommodationDto.TagDto tag1 = new AccommodationDto.TagDto("OFFICE", "전용 업무 공간");
        AccommodationDto.TagDto tag2 = new AccommodationDto.TagDto("SPA", "스파");
        AccommodationDto.TagDto tag3 = new AccommodationDto.TagDto("PRINTER", "사무기기");

        AccommodationDto acc1 = new AccommodationDto(
                1L, "천안 호두과자 워케이션 리조트", "충남 천안시 서북구 불당 32길", 6, 4, "인근 자연공원과, 호두과자 서비스", 60000, Set.of(tag1, tag2)
        );
        AccommodationDto acc2 = new AccommodationDto(
                2L, "빈센트 부안고흐 펜션", "전북 부안군 변산면 대항리 567", 5, 5, "인근 해변과 항구, 낭만의 곤충 생태공원까지", 50000, Set.of(tag3, tag2)
        );
        mockDtoList = List.of(acc1, acc2);

    }

    @Test
    @DisplayName("1. 기본 리스트 호출 -> 200 응답")
    public void 기본리스트_호출테스트() throws Exception {
        //given
        given(accService.getBaseList()).willReturn(mockDtoList);

        //w+then
        mockMvc.perform(get("/accommodations/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("천안 호두과자 워케이션 리조트")))
                .andExpect(jsonPath("$[1].address", is("전북 부안군 변산면 대항리 567")))
                .andDo(print());

        verify(accService, times(1)).getBaseList();
    }
    
    @Test
    @DisplayName("2. 검색 파라미터 전달")
    public void 검색API_테스트() throws Exception {
        //given
        given(accService.search(any(AccomSearchFilterDto.class)))
                .willReturn(mockDtoList);
        
        //w+then
        mockMvc.perform(get("/accommodations/search")
                        .param("name", "빈센트")
                        .param("minPrice", "40000")
                        .param("maxPrice", "70000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());

        verify(accService, times(1)).search(any(AccomSearchFilterDto.class));

    }

    @Test
    @DisplayName("3. 추천 로직 호출")
    public void 추천로직_테스트() throws Exception {
        //given
        given(accService.recommend(any(AccomSearchFilterDto.class)))
                .willReturn(mockDtoList);
        //w+then
        mockMvc.perform(get("/accommodations/recommend")
                        .param("name","고흐")
                        .param("minPrice", "50000")
                        .param("maxPrice", "70000")
                        .param("enableScore","true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());

        verify(accService, times(1)).recommend(any(AccomSearchFilterDto.class));
    }





}
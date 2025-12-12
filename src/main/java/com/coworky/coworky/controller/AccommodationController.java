package com.coworky.coworky.controller;

import com.coworky.coworky.dto.AccomSearchFilterDto;
import com.coworky.coworky.dto.AccomSearchRecommendDto;
import com.coworky.coworky.dto.AccommodationDto;
import com.coworky.coworky.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationService accService;

    // 전체 조회 id순 정렬.
    @GetMapping("/")
    public List<AccommodationDto> baseList(){
        return accService.getBaseList();
    }

    // 검색 API
    @GetMapping("/search")
    public List<AccommodationDto> searchList(AccomSearchFilterDto filter){
        return accService.search(filter);
    }

    // 추천 Api
    @GetMapping("/recommend")
    public List<AccommodationDto> recommend(AccomSearchFilterDto filter) {

        //AccomSearchFilterDto filter = recommendDto.recommendFilter();
        return accService.recommend(filter);
    };
}

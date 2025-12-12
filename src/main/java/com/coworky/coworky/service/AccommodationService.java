package com.coworky.coworky.service;

import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.dto.AccomSearchFilterDto;
import com.coworky.coworky.dto.AccommodationDto;
import com.coworky.coworky.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {

    private final AccommodationRepository accRepository;

    public List<AccommodationDto> search(AccomSearchFilterDto filter){
        return accRepository.search(filter)
                            .stream()
                            .map(this::toDto)
                            .collect(Collectors.toList());
    }

    // 초기 화면 (id순 조회)
    public List<AccommodationDto> getBaseList(){
        List<Accommodation> ascIdList = accRepository.findAll(Sort.by("id"));
        return ascIdList.stream()
                        .map(this::toDto)
                        .collect(Collectors.toList());
    }

    // 추천 숙소 로직
    public List<AccommodationDto> recommend(AccomSearchFilterDto accSearchfilter){
        AccomSearchFilterDto recommendFilter = new AccomSearchFilterDto();

        // 1. 지역 기준 추천
        regionRecommend(accSearchfilter, recommendFilter);

        // 2. 가격 추천
        priceRecommend(accSearchfilter, recommendFilter);

        // 3. 키워드 추천
        keywordRecommend(accSearchfilter, recommendFilter);

        // 4. enableScore 적용
        if (accSearchfilter.isEnableScore()){
            recommendFilter.setEnableScore(true);
        }

        List<Accommodation> result = accRepository.search(recommendFilter);

        return result.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
    }

    // 1. 지역 기준 로직
    private void regionRecommend(AccomSearchFilterDto origin, AccomSearchFilterDto recommend) {
        if (origin.getRegion() != null && !origin.getRegion().isEmpty()) {
            recommend.setRegion(origin.getRegion());
        }
    }

    // 2. 가격 추천 로직(설정한 최소금액 -10% ~ 설정한 최대 금액 +10%)
    private void priceRecommend(AccomSearchFilterDto origin, AccomSearchFilterDto recommend) {
        if (origin.getMinPrice() == null || origin.getMaxPrice() == null) return;

        BigDecimal min = origin.getMinPrice();
        BigDecimal max = origin.getMaxPrice();

        BigDecimal tenMin = min.multiply(new BigDecimal("0.1"));
        BigDecimal tenMax = max.multiply(new BigDecimal("0.1"));

        BigDecimal recommendMin = min.subtract(tenMin);
        if(recommendMin.compareTo(BigDecimal.ZERO) < 0){
            recommendMin = BigDecimal.ZERO;
        }
        BigDecimal recommendMax = max.add(tenMax);

        recommend.setMinPrice(recommendMin);
        recommend.setMaxPrice(recommendMax);
    }

    // 3. 키워드 추천 로직
    private void keywordRecommend(AccomSearchFilterDto origin, AccomSearchFilterDto recommend) {

        if(origin.getName() != null && !origin.getName().isEmpty()){
            recommend.setName(origin.getName());
        }

        if(origin.getDescription() != null && !origin.getDescription().isEmpty()){
            recommend.setDescription(origin.getDescription());
        }

        if(origin.getDetailInfo() != null && !origin.getDetailInfo().isEmpty()){
            recommend.setDetailInfo(origin.getDetailInfo());
        }

        if(origin.getAddress() != null && !origin.getAddress().isEmpty()){
            recommend.setAddress(origin.getAddress());
        }

        if(origin.getCity() != null && !origin.getCity().isEmpty()){
            recommend.setCity(origin.getCity());
        }
    }

    public AccommodationDto toDto(Accommodation acc){

        AccommodationDto accDto = new AccommodationDto(
                acc.getId(),
                acc.getName(),
                acc.getLocation().getAddress(),
                acc.getCapacity(),
                acc.getDescription(),
                acc.getBasePrice().intValue(),
                acc.getTags().stream()
                             .map(tag ->
                                     new AccommodationDto.TagDto(
                                             tag.name(),
                                             tag.getAccommodationTags()
                                     )
                             )
                            .collect(Collectors.toSet())
        );
        return accDto;
    }
}

package com.coworky.coworky.service;

import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.dto.AccomSearchFilterDto;
import com.coworky.coworky.repository.AccommodationRepository;
import com.coworky.coworky.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    @Mock
    AccommodationRepository accRepository;

    @InjectMocks
    AccommodationService accService;

    @Test
    public void 검색성공시_DTO반환() throws Exception {
        //given
        AccomSearchFilterDto searchFilter = new AccomSearchFilterDto();
        Accommodation accA = TestUtils.createMockAccommodationA();
        Accommodation accB = TestUtils.createMockAccommodationB();

        List<Accommodation> mockEntity = List.of(accA, accB);

        //when

        //then
    }

}
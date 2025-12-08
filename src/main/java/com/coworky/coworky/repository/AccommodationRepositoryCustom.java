package com.coworky.coworky.repository;

import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.dto.AccomSearchFilterDto;

import java.util.List;

public interface AccommodationRepositoryCustom {
    List<Accommodation> search(AccomSearchFilterDto filter);
}

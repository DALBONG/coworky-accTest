package com.coworky.coworky.repository;

import com.coworky.coworky.domain.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccommodationRepository
        extends JpaRepository<Accommodation, Long>,
                AccommodationRepositoryCustom {

};

package com.coworky.coworky.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TB_Location")
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "country", nullable = false, length = 100)
    private String country; // 나라

    @Column(name = "region", nullable = false, length = 100)
    private String region; // 지역

    @Column(name = "city", nullable = false, length = 100)
    private String city; // 도시

    @Column(name = "address", nullable = false, length = 1500)
    private String address; // 주소

    @Column(name = "latitude", nullable = false, precision = 9, scale = 6, unique = true)
    private BigDecimal latitude; // 위도

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6, unique = true)
    private BigDecimal longitude; // 경도

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; // 생성일자

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate; // 수정 일자

    public static Location createLocation(
        String country,
        String region,
        String city,
        String address,
        BigDecimal latitude,
        BigDecimal longitude
    ) {
        Location loca = new Location();
        loca.country = country;
        loca.region = region;
        loca.city = city;
        loca.address = address;
        loca.latitude = latitude;
        loca.longitude = longitude;

        return loca;

    }
}

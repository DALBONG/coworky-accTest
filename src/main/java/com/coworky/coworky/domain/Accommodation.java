package com.coworky.coworky.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TB_Accommodation")
@Getter @Setter
public class Accommodation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;//숙소 이름

    @Column(name = "description", nullable = false, length = 1000)
    private String description; // 숙소 소개

    @Column(name = "base_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal basePrice; // 기본 금액

    @Column(name = "weekday_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal weekdayPrice; // 주중 금액

    @Column(name = "weekend_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal weekendPrice; // 주말 금액

    @Column(name = "capacity", nullable = false)
    private Integer capacity; // 최대 인원

    @Column(name = "standard_capacity", nullable = false)
    private Integer standardCapacity; // 기준 인원

    @Column(name = "detail_info", nullable = false, length = 4000)
    private String detailInfo; // 상세 정보

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; // 게시 일

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate; // 수정 일

    @ElementCollection(targetClass = AccommodationTags.class, fetch = LAZY)
    @CollectionTable(name= "TB_Accommodation_Tags",
        joinColumns = @JoinColumn(name = "accommodation_id"))
    @Enumerated(STRING)
    @Column(name = "tags", nullable = false)
    private Set<AccommodationTags> tags = new HashSet<>(); // 편의 시설  [ WIFI, OFFICE, PRINTER, FITNESS, SPA, CAFETERIA, KITCHEN ];

    @Enumerated(STRING)
    @Column(nullable = false)
    private AccommodationType type; // 숙소 유형 [ HOTEL, PENSION, RESORT, STAY ]

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private AccommodationStatus status; // 예약 상태 [ AVAILABLE, UNAVAILABLE ]

    // --- Location 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // Accommodation 생성 메소드
    public static Accommodation createAccommodation(
        String name,
        String description,
        BigDecimal basePrice,
        BigDecimal weekdayPrice,
        BigDecimal weekendPrice,
        Integer capacity,
        Integer standardCapacity,
        String detailInfo,
        Set<AccommodationTags> tags,
        AccommodationType type,
        Location location
    ){
        Accommodation acc = new Accommodation();
        acc.name = name;
        acc.description = description;
        acc.basePrice = basePrice;
        acc.weekdayPrice = weekdayPrice;
        acc.weekendPrice = weekendPrice;
        acc.capacity = capacity;
        acc.standardCapacity = standardCapacity;
        acc.detailInfo = detailInfo;
        acc.tags = tags != null ? tags : new HashSet<>();
        acc.type = type;
        acc.status = AccommodationStatus.AVAILABLE;
        acc.location = location;
        return acc;
    }
}

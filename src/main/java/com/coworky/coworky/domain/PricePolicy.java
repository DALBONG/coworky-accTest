package com.coworky.coworky.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TB_PricePolicy")
@Getter
@Setter
public class PricePolicy {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "price_policy_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 1000)
    private String name;

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(STRING)
    @Column(name = "type", nullable = false)
    private PricePolicyStatus type; // [ SEASON, WEEKEND, LONGTERM ]

    @Column(name = "min_nights")
    private int minNights;

    @Column(name = "max_nights")
    private int maxNights;

    @Column(name = "min_guests")
    private int minGuests;

    @Column(name = "max_guests")
    private int maxGuests;

    @Column(name = "adjustment_value", precision = 15, scale = 2)
    private BigDecimal adjustmentValue;

    @Enumerated(STRING)
    @Column(name = "adjustment_type", nullable = false)
    private AdjustmentType adjustmentType; // [ FIXED, PERCENT, DELTA ]

    @Enumerated(STRING)
    @Column(name = "is_active", nullable = false)
    private ActiveType isActive; // [ ACTIVE, INACTIVE ]

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    // --- User 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- Accommodation 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    public LocalDateTime getStartDate() {
        return startDate != null ? startDate : LocalDateTime.MIN;
    }

    public LocalDateTime getEndDate() {
        return endDate != null ? endDate : LocalDateTime.MAX;
    }


}

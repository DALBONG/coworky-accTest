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

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TB_Promotion")
@Getter
@Setter
public class Promotion {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    @Column(name = "promotion_code", nullable = false, length = 50, unique = true)
    private String promotionCode;

    @Column(name = "promotion_name", nullable = false, length = 1000)
    private String promotionName;

    @Column(name = "promotion_description", length = 4000)
    private String promotionDescription;

    @Enumerated(STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType; // [ PERCENT, FIXED ]

    @Column(name = "discount_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "max_discount_amount", precision = 15, scale = 2)
    private BigDecimal maxDiscountAmount;

    @Column(name = "min_order_amount", precision = 15, scale = 2)
    private BigDecimal minOrderAmount;

    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_to", nullable = false)
    private LocalDateTime validTo;

    @Column(name = "max_uses")
    private Integer maxUses;

    @Column(name = "per_user_limit")
    private Integer perUserLimit;

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

}

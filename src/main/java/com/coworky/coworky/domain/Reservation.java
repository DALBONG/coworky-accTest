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
@Table(name = "TB_Reservation")
@Getter @Setter
public class Reservation {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDateTime checkOutDate;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "total_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "number_of_guest", nullable = false)
    private Integer guest;

    @Column(name = "memo", length = 4000)
    private String memo;

    @Enumerated(STRING)
    @Column(name ="status", nullable = false)
    private ReservationStatus status; // [ PENDING, CONFIRMED, CANCELLED, COMPLETED ]

    // --- user 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- Accommodation 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

}

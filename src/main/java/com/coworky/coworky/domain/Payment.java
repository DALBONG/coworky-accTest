package com.coworky.coworky.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TB_Payment")
@Getter @Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    @Column(name = "receipt_url",  nullable = false, length = 200)
    private String receiptUrl;

    @Enumerated(STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod paymentMethod; // [ COMPANY, CARD, TRANSFER ]

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus paymentStatus; // [ PENDING, PAID, FAILED, REFUNDED ]

    // --- Reservation 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // --- Promotion 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

}

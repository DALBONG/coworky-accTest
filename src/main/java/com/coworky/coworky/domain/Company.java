package com.coworky.coworky.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TB_Company")
@Getter @Setter
public class Company {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String name;

    @CreationTimestamp
    @Column(name = "connected_date", nullable = false)
    private LocalDateTime connectedDate;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private ConnectStatus status; // [ CONNECTED, TERMINATED ]

    public Company() {}

    public Company(String name, ConnectStatus status) {
        this.name = name;
        this.status = status;
    }
}

package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Seller")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;


    @Column(nullable = false)
    private String password;

    @Column(name = "store_name", nullable = false, unique = true)
    private String storeName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE','SUSPENDED') DEFAULT 'ACTIVE'")
    @Builder.Default
    private SellerStatus status = SellerStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum SellerStatus { ACTIVE, SUSPENDED }
}
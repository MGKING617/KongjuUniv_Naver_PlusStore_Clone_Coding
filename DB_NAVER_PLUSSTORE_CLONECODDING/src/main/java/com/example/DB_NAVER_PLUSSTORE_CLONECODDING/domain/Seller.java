package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Seller")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Seller {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "store_name")
    private String storeName;

    // 필요 시 phone, status 등 추가
}
package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Cart")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(CartId.class)
public class Cart {
    @Id @Column(name = "customer_id")
    private Long customerId;

    @Id @Column(name = "option_id")
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", insertable = false, updatable = false)
    private ProductOption option;

    private Integer quantity;

    // 비즈니스 로직: 수량 증가
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
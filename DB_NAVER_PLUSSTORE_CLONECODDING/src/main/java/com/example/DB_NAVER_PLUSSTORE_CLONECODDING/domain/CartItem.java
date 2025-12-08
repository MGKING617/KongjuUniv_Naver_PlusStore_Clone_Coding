package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "Cartitem")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(CartItemId.class)
public class CartItem {

    @Id
    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column(name = "option_id")
    private Long optionId;

    @Column(nullable = false)
    private Integer quantity;
}
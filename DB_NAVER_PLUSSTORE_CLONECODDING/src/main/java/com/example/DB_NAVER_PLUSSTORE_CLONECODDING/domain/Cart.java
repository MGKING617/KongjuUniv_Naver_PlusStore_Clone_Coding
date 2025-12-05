package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cart")
@Getter
@NoArgsConstructor
public class Cart {

    @EmbeddedId
    private CartId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("optionId")
    @JoinColumn(name = "option_id")
    private ProductOption option;

    @Column(nullable = false)
    private int quantity;

    public Cart(Customer customer, ProductOption option, int quantity) {
        this.id = new CartId(customer.getId(), option.getId()); // 복합키 생성
        this.customer = customer;
        this.option = option;
        this.quantity = quantity;
    }

    // [추가] 수량 증가 메서드: 이미 담긴 상품을 또 담을 때 사용
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}

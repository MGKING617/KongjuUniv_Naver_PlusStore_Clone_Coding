package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ProductOption")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductOption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "option_name")
    private String optionName;

    @Column(name = "extra_price")
    private BigDecimal extraPrice;

    private Integer stock;

    // 비즈니스 로직: 재고 감소
    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }
}
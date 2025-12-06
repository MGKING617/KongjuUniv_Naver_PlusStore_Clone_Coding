package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "OrderItem")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(OrderItemId.class)
public class OrderItem {
    @Id @Column(name = "order_id")
    private Long orderId;

    @Id @Column(name = "line_no")
    private Integer lineNo;

    @Column(name = "option_id")
    private Long optionId;

    private Integer quantity;
    private BigDecimal unitPriceSnapshot;
    private BigDecimal subtotal;
}
package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;



@Entity
@Table(name = "OrderItem")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(OrderItemId.class)
public class OrderItem {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Id
    @Column(name = "line_no")
    private Integer lineNo;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption productOption;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price_snapshot", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPriceSnapshot;

    @Column(name = "extra_price_snapshot", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal extraPriceSnapshot = BigDecimal.ZERO;

    @Column(name = "discount_snapshot", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal discountSnapshot = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
}
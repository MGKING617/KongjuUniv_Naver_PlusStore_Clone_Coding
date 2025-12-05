package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "OrderItem")
@Getter
@NoArgsConstructor
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption option;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price_snapshot", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPriceSnapshot;

    @Column(name = "extra_price_snapshot", nullable = false, precision = 12, scale = 2)
    private BigDecimal extraPriceSnapshot;

    @Column(name = "discount_snapshot", nullable = false, precision = 12, scale = 2)
    private BigDecimal discountSnapshot;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    public OrderItem(Order order, ProductOption option, int quantity, int lineNo) {
        this.id = new OrderItemId(order.getId(), lineNo);
        this.order = order;
        this.option = option;
        this.quantity = quantity;


        // 기본가(Product 가격)
        this.unitPriceSnapshot = option.getProduct().getPrice();
        // 추가금(Option 가격)
        this.extraPriceSnapshot = option.getExtraPrice();
        // 할인(일단 0원 처리)
        this.discountSnapshot = BigDecimal.ZERO;

        // 합계 = (기본가 + 추가금) * 수량
        BigDecimal pricePerItem = unitPriceSnapshot.add(extraPriceSnapshot);
        this.subtotal = pricePerItem.multiply(BigDecimal.valueOf(quantity));
    }
}

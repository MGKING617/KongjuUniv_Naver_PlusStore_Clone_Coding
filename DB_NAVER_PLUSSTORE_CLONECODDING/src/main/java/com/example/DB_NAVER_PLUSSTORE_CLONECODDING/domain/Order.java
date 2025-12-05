package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "`Order`") // 실제 테이블명은 Order, 역따옴표로 escape
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    public enum OrderStatus {
        PENDING, PAID, SHIPPED, CANCELLED, REFUNDED
    }

    public static Order createOrder(Customer customer) {
        Order order = new Order();
        order.customer = customer;
        order.orderedAt = LocalDateTime.now(); // 현재 시간
        order.status = OrderStatus.PENDING;    // 대기 상태
        order.totalAmount = BigDecimal.ZERO;   // 일단 0원 (나중에 더함)
        return order;
    }

    // [추가] 총 주문 금액 업데이트
    public void calculateTotalAmount(BigDecimal amount) {
        this.totalAmount = amount;
    }
}

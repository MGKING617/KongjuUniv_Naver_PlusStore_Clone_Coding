package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    // [1] 주문하기
    public Long createOrder(Long customerId) {
        // 1. 장바구니 가져오기
        List<Cart> cartList = cartRepository.findByCustomerId(customerId);
        if (cartList.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어있습니다.");
        }

        // 2. 총 결제 금액 계산
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart c : cartList) {
            BigDecimal price = c.getOption().getProduct().getPrice().add(c.getOption().getExtraPrice());
            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(c.getQuantity())));
        }

        // 3. 주문 생성
        Order order = Order.builder()
                .customerId(customerId)
                .orderedAt(LocalDateTime.now())
                .status("PAID") // 결제 완료 상태라고 가정
                .totalAmount(totalAmount)
                .build();
        orderRepository.save(order);

        // 4. 상세 내역 저장 및 재고 차감
        int lineNo = 1;
        for (Cart c : cartList) {
            ProductOption option = c.getOption();

            // 재고 줄이기 (ProductOption에 만든 메서드 사용)
            option.removeStock(c.getQuantity());

            OrderItem item = OrderItem.builder()
                    .orderId(order.getOrderId())
                    .lineNo(lineNo++)
                    .optionId(option.getOptionId())
                    .quantity(c.getQuantity())
                    .unitPriceSnapshot(option.getProduct().getPrice().add(option.getExtraPrice()))
                    .subtotal(BigDecimal.ZERO)
                    .build();
            orderItemRepository.save(item);
        }

        // 5. 장바구니 비우기
        cartRepository.deleteByCustomerId(customerId);

        return order.getOrderId();
    }
}
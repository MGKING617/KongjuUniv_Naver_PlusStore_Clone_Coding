package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public Long createOrder(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        List<CartItem> cartItems = cartItemRepository.findByCustomerId(customerId);
        if (cartItems.isEmpty()) throw new IllegalArgumentException("장바구니 비어있음");

        Order order = Order.builder()
                .customer(customer)
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem ci : cartItems) {
            ProductOption option = productOptionRepository.findById(ci.getOptionId()).orElseThrow();

            BigDecimal finalPrice = option.getProduct().getPrice().add(option.getExtraPrice());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productOption(option)
                    .quantity(ci.getQuantity())
                    .unitPriceSnapshot(finalPrice)
                    .subtotal(finalPrice.multiply(BigDecimal.valueOf(ci.getQuantity())))
                    .build();

            order.getOrderItems().add(orderItem);
            total = total.add(orderItem.getSubtotal());
        }

        order.setTotalAmount(total);
        orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return order.getOrderId();
    }


    public List<Order> getOrders(Long customerId) {
        return orderRepository.findByCustomer_CustomerId(customerId);
    }
}
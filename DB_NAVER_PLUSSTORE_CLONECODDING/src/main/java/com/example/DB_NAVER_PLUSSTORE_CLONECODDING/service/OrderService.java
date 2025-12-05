package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.CartOrderDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.CartDetailDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    // 장바구니 담기
    public void addToCart(CartOrderDto.CartAddRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("고객 없음"));
        ProductOption option = productOptionRepository.findById(request.getOptionId())
                .orElseThrow(() -> new IllegalArgumentException("옵션 없음"));

        CartId cartId = new CartId(customer.getId(), option.getId());
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart != null) {
            cart.addQuantity(request.getQuantity());
        } else {
            cart = new Cart(customer, option, request.getQuantity());
            cartRepository.save(cart);
        }
    }

    // 주문 생성 (재고 차감 포함)
    public Long createOrder(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("고객 없음"));

        List<Cart> cartList = cartRepository.findByCustomerId(customerId);
        if (cartList.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어있습니다.");
        }

        // 주문 생성
        Order order = Order.createOrder(customer);
        orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        int lineNo = 1;

        for (Cart cart : cartList) {
            ProductOption option = cart.getOption();

            //재고 차감
            option.removeStock(cart.getQuantity());

            OrderItem orderItem = new OrderItem(order, option, cart.getQuantity(), lineNo++);
            orderItemRepository.save(orderItem);

            totalAmount = totalAmount.add(orderItem.getSubtotal());
        }

        order.calculateTotalAmount(totalAmount);
        cartRepository.deleteAll(cartList); // 장바구니 비우기

        return order.getId();
    }

    @lombok.Data
    public static class CartDetailDto {
        private Long optionId;
        private String productName;
        private String optionName;
        private int quantity;
        private java.math.BigDecimal price;
    }

    // 장바구니 목록 조회 메서드
    public List<CartDetailDto> getCartList(Long customerId) {
        List<Cart> carts = cartRepository.findByCustomerId(customerId);

        return carts.stream().map(cart -> {
            CartDetailDto dto = new CartDetailDto();
            dto.setOptionId(cart.getOption().getId());
            dto.setProductName(cart.getOption().getProduct().getName());
            dto.setOptionName(cart.getOption().getOptionName());
            dto.setQuantity(cart.getQuantity());
            // 가격 = (상품기본가 + 옵션추가금)
            dto.setPrice(cart.getOption().getProduct().getPrice().add(cart.getOption().getExtraPrice()));
            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }
}
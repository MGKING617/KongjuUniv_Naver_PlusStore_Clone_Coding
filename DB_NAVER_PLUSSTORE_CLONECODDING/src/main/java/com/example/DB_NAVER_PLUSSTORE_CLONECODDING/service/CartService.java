package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.CartDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductOptionRepository productOptionRepository;

    // 장바구니 담기
    public void addToCart(CartDto.AddRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없습니다."));

        ProductOption option = productOptionRepository.findById(request.getOptionId())
                .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));

        CartId cartId = new CartId(customer.getId(), option.getId());

        // 이미 장바구니에 있는지 확인
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart != null) {
            cart.addQuantity(request.getQuantity());
        } else {
            cart = new Cart(customer, option, request.getQuantity());
            cartRepository.save(cart);
        }
    }
}
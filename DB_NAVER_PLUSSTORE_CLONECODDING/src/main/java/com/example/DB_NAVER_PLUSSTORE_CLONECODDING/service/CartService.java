package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.CartDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductOptionRepository optionRepository;

    // [1] 장바구니 담기
    public void addToCart(CartDto.AddRequest req) {
        Customer customer = customerRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("고객 없음"));
        ProductOption option = optionRepository.findById(req.getOptionId())
                .orElseThrow(() -> new IllegalArgumentException("옵션 없음"));

        // 복합키 생성 (getCustomerId, getOptionId 사용)
        CartId cartId = new CartId(customer.getCustomerId(), option.getOptionId());

        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart != null) {
            // 이미 있으면 수량 추가
            cart.addQuantity(req.getQuantity());
        } else {
            // 없으면 새로 생성 (Builder 사용)
            cart = Cart.builder()
                    .customerId(customer.getCustomerId())
                    .optionId(option.getOptionId())
                    .option(option)
                    .quantity(req.getQuantity())
                    .build();
            cartRepository.save(cart);
        }
    }

    // [2] 장바구니 목록 조회
    public List<CartDto.Response> getCartList(Long customerId) {
        return cartRepository.findByCustomerId(customerId).stream().map(cart -> {
            ProductOption option = cart.getOption();
            CartDto.Response res = new CartDto.Response();
            res.setOptionId(option.getOptionId());
            res.setProductName(option.getProduct().getName());
            res.setOptionName(option.getOptionName());
            res.setQuantity(cart.getQuantity());
            // 가격 계산 (기본가 + 옵션가)
            res.setPrice(option.getProduct().getPrice().add(option.getExtraPrice()));
            return res;
        }).collect(Collectors.toList());
    }
}
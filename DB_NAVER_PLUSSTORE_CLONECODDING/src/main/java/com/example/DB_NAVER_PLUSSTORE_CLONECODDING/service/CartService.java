package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public void addToCart(Map<String, Object> body) {
        try {
            // 1. 데이터 파싱 (로그 추가)
            System.out.println(">>> [장바구니 요청 데이터]: " + body);

            // 숫자가 String으로 올 수도 있고 Integer로 올 수도 있어서 안전하게 변환
            Long customerId = Long.valueOf(String.valueOf(body.get("customerId")));
            Long optionId = Long.valueOf(String.valueOf(body.get("optionId")));
            Integer quantity = Integer.valueOf(String.valueOf(body.get("quantity")));

            // 2. 복합키 생성
            CartItemId id = new CartItemId(customerId, optionId);

            // 3. DB 조회 및 저장
            Optional<CartItem> existingItem = cartItemRepository.findById(id);

            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + quantity);
                cartItemRepository.save(item);
                System.out.println(">>> [장바구니] 수량 증가 완료: " + item.getQuantity());
            } else {
                CartItem cartItem = CartItem.builder()
                        .customerId(customerId)
                        .optionId(optionId)
                        .quantity(quantity)
                        .build();
                cartItemRepository.save(cartItem);
                System.out.println(">>> [장바구니] 신규 상품 담기 완료");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 에러 발생 시 로그 출력
            throw new RuntimeException("장바구니 담기 중 오류 발생");
        }
    }

    public List<Map<String, Object>> getCartList(Long customerId) {
        List<CartItem> items = cartItemRepository.findByCustomerId(customerId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (CartItem item : items) {
            ProductOption option = productOptionRepository.findById(item.getOptionId()).orElse(null);
            if (option == null) continue;

            BigDecimal basePrice = option.getProduct().getPrice();
            BigDecimal extraPrice = option.getExtraPrice();
            BigDecimal unitPrice = basePrice.add(extraPrice);

            Map<String, Object> map = new HashMap<>();
            map.put("productName", option.getProduct().getName());
            map.put("optionName", option.getOptionName());
            map.put("price", unitPrice);
            map.put("quantity", item.getQuantity());
            // 프론트엔드에서 이미지를 보여주기 위해 추가
            map.put("imageUrl", option.getProduct().getImageUrl());

            result.add(map);
        }
        return result;
    }
}
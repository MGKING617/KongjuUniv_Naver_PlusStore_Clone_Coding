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

    // 장바구니 담기
    @Transactional
    public void addToCart(Map<String, Object> body) {
        try {
            Long customerId = Long.valueOf(String.valueOf(body.get("customerId")));
            Long optionId = Long.valueOf(String.valueOf(body.get("optionId")));
            Integer quantity = Integer.valueOf(String.valueOf(body.get("quantity")));

            CartItemId id = new CartItemId(customerId, optionId);
            Optional<CartItem> existingItem = cartItemRepository.findById(id);

            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + quantity);
                cartItemRepository.save(item);
            } else {
                CartItem cartItem = CartItem.builder()
                        .customerId(customerId)
                        .optionId(optionId)
                        .quantity(quantity)
                        .build();
                cartItemRepository.save(cartItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("장바구니 담기 오류");
        }
    }


    @Transactional
    public void removeCartItem(Long customerId, Long optionId) {
        CartItemId id = new CartItemId(customerId, optionId);
        cartItemRepository.deleteById(id);
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
            map.put("optionId", item.getOptionId());
            map.put("productName", option.getProduct().getName());
            map.put("optionName", option.getOptionName());
            map.put("price", unitPrice);
            map.put("quantity", item.getQuantity());
            map.put("imageUrl", option.getProduct().getImageUrl());

            result.add(map);
        }
        return result;
    }
}
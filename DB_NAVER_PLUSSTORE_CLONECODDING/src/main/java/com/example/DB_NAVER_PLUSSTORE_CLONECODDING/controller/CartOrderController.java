package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.CartDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.OrderDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.CartService;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartOrderController {

    private final CartService cartService;
    private final OrderService orderService;

    // 장바구니 담기 API
    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDto.AddRequest request) {
        cartService.addToCart(request);
        return ResponseEntity.ok("장바구니에 담겼습니다.");
    }

    // 주문하기 API
    @PostMapping("/orders")
    public ResponseEntity<Long> createOrder(@RequestBody OrderDto.CreateRequest request) {
        Long orderId = orderService.createOrder(request.getCustomerId());
        return ResponseEntity.ok(orderId);
    }
}
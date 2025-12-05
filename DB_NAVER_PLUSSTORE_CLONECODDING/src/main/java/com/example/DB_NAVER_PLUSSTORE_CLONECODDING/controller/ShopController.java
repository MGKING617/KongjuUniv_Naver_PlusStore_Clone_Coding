package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.CartOrderDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {
    private final OrderService orderService;

    @PostMapping("/cart")
    public ResponseEntity<String> addToCart(@RequestBody CartOrderDto.CartAddRequest req) {
        orderService.addToCart(req);
        return ResponseEntity.ok("장바구니 담기 성공");
    }
    @PostMapping("/order")
    public ResponseEntity<Long> createOrder(@RequestBody CartOrderDto.OrderCreateRequest req) {
        return ResponseEntity.ok(orderService.createOrder(req.getCustomerId()));
    }

    @GetMapping("/cart/{customerId}")
    public ResponseEntity<List<OrderService.CartDetailDto>> getCartList(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getCartList(customerId));
    }
}
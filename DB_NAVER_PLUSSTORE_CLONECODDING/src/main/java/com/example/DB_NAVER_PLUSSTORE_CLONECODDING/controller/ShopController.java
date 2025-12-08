package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ShopController {

    private final CartService cartService;


    @PostMapping("/cart")
    public ResponseEntity<String> addCart(@RequestBody Map<String, Object> body) {
        cartService.addToCart(body);
        return ResponseEntity.ok("장바구니에 담겼습니다.");
    }

    @GetMapping("/cart/{customerId}")
    public ResponseEntity<List<Map<String, Object>>> getCart(@PathVariable Long customerId) {
        List<Map<String, Object>> list = cartService.getCartList(customerId);
        return ResponseEntity.ok(list);
    }
}
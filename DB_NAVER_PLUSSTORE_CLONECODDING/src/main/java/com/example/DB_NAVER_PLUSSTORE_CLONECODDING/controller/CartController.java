package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.CartDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;

    // 장바구니 담기
    // POST http://localhost:8080/api/shop/cart
    @PostMapping("")
    public String addToCart(@RequestBody CartDto.AddRequest request) {
        cartService.addToCart(request);
        return "장바구니에 담겼습니다.";
    }

    // 내 장바구니 조회
    // GET http://localhost:8080/api/shop/cart/1
    @GetMapping("/{customerId}")
    public List<CartDto.Response> getCartList(@PathVariable Long customerId) {
        return cartService.getCartList(customerId);
    }
}
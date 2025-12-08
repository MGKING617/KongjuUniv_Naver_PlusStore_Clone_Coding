package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.OrderDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public Long placeOrder(@RequestBody OrderDto.CreateRequest request) {
        return orderService.createOrder(request.getCustomerId());
    }
}
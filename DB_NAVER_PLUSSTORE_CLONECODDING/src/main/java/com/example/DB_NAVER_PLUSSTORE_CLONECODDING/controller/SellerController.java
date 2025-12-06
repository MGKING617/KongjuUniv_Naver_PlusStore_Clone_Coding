package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.ProductDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // 리액트 접속 허용
public class SellerController {

    private final ProductService productService;

    // 상품 등록
    // POST http://localhost:8080/api/seller/products
    @PostMapping("/products")
    public String createProduct(@RequestBody ProductDto.CreateRequest request) {
        productService.createProduct(request);
        return "상품 등록 완료!";
    }
}
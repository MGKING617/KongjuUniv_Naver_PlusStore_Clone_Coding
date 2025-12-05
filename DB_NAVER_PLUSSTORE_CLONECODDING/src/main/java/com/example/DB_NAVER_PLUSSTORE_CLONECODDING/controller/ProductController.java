package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.ProductDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 전체 상품 목록 조회
    @GetMapping
    public List<ProductDto.Summary> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품 상세 조회 (ID로 조회)
    @GetMapping("/{productId}")
    public ProductDto.Detail getProductDetail(@PathVariable Long productId) {
        return productService.getProductDetail(productId);
    }
}
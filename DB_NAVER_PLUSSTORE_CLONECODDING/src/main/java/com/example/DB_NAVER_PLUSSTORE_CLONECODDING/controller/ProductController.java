package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.ProductDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    // 전체 상품 목록 조회
    // GET http://localhost:8080/api/products
    @GetMapping("")
    public List<ProductDto.Summary> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품 상세 조회
    // GET http://localhost:8080/api/products/1
    @GetMapping("/{id}")
    public ProductDto.Detail getProductDetail(@PathVariable Long id) {
        return productService.getProductDetail(id);
    }
}
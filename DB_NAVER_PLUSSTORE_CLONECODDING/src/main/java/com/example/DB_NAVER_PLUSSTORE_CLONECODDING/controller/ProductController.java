package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Product;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long sellerId
    ) {
        if (sellerId != null) {
            return productRepository.findBySeller_SellerId(sellerId);
        }
        if (categoryId != null) {
            return productRepository.findByCategory_CategoryId(categoryId);
        }
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.of(productRepository.findById(id));
    }
}
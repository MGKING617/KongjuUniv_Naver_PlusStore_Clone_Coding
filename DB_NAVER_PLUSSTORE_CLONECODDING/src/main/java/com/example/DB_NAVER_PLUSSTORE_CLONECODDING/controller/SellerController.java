package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SellerController {

    private final SellerService sellerService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;

    @GetMapping("/sellers")
    public List<Seller> getAllSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        System.out.println(">>> [API] 스토어 목록 조회 요청됨. 조회된 스토어 수: " + sellers.size());
        return sellers;
    }

    @GetMapping("/sellers/{sellerId}")
    public ResponseEntity<Seller> getSellerInfo(@PathVariable Long sellerId) {
        return ResponseEntity.of(sellerRepository.findById(sellerId));
    }


    @PostMapping("/seller/products")
    public ResponseEntity<String> createProduct(
            @RequestParam("productData") String productDataJson,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> body = mapper.readValue(productDataJson, Map.class);

        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/uploads/";
            File directory = new File(projectPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            imageUrl = "http://localhost:8080/images/" + fileName;
        }

        if (imageUrl != null) {
            body.put("imageUrl", imageUrl);
        }

        productService.createProduct(body);
        return ResponseEntity.ok("상품 등록 완료");
    }


    @PostMapping("/sellers/register")
    public ResponseEntity<String> register(@RequestBody Seller seller) {
        sellerService.register(seller);
        return ResponseEntity.ok("가입 성공");
    }

    @PostMapping("/sellers/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        Seller seller = sellerService.login(body.get("loginId"), body.get("password"));
        if (seller != null) return ResponseEntity.ok(seller);
        return ResponseEntity.status(401).body("로그인 실패");
    }

    @PostMapping("/seller/categories")
    public ResponseEntity<String> createCategory(@RequestBody Map<String, Object> body) {
        Long sellerId = Long.valueOf(body.get("sellerId").toString());
        String categoryName = (String) body.get("categoryName");
        Seller seller = sellerRepository.findById(sellerId).orElseThrow();
        Category category = Category.builder().seller(seller).categoryName(categoryName).build();
        categoryRepository.save(category);
        return ResponseEntity.ok("카테고리 생성 완료");
    }

    @GetMapping("/seller/categories/{sellerId}")
    public List<Category> getSellerCategories(@PathVariable Long sellerId) {
        return categoryRepository.findBySeller_SellerId(sellerId);
    }

    @GetMapping("/seller/products/{sellerId}")
    public List<Product> getMyProducts(@PathVariable Long sellerId) {
        return productRepository.findBySeller_SellerId(sellerId);
    }
}
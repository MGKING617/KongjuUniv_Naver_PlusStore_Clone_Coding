package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.controller;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Customer;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer) {
        customerService.register(customer);
        return ResponseEntity.ok("가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        Customer customer = customerService.login(body.get("loginId"), body.get("password"));
        if (customer != null) return ResponseEntity.ok(customer);
        return ResponseEntity.status(401).body("로그인 실패");
    }
}
package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Cart;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, CartId> {
    // 특정 고객의 장바구니 목록 조회
    List<Cart> findByCustomerId(Long customerId);
}
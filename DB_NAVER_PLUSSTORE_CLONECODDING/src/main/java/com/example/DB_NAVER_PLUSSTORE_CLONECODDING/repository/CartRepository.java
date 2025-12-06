package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Cart;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, CartId> {
    // 1. 특정 고객의 장바구니 목록 다 가져오기
    List<Cart> findByCustomerId(Long customerId);

    // 2. 특정 고객의 장바구니 싹 비우기 (주문 완료 시)
    void deleteByCustomerId(Long customerId);
}
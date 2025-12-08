package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.CartItem;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.CartItemId; // import 확인
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    List<CartItem> findByCustomerId(Long customerId);
}
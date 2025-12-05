package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품 목록 조회 (최신순) - N+1 문제 방지를 위해 Fetch Join 사용 권장
    @Query("SELECT p FROM Product p JOIN FETCH p.seller WHERE p.status = 'ON'")
    List<Product> findAllActiveProducts();
}
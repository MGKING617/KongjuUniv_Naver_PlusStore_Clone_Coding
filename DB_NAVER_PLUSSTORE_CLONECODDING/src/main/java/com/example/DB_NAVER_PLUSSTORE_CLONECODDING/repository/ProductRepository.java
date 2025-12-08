package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_CategoryId(Long categoryId);
    List<Product> findBySeller_SellerId(Long sellerId);
}
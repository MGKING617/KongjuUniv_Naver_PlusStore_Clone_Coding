package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findBySeller_SellerId(Long sellerId);
}
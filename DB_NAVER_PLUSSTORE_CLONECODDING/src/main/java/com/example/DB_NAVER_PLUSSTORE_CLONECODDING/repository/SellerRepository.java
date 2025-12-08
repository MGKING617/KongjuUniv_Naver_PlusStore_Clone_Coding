package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByLoginId(String loginId);
}
package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByLoginId(String loginId);
}
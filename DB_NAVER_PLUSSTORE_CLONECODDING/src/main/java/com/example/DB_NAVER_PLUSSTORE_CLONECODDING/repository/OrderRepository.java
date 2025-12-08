package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer_CustomerId(Long customerId);
}
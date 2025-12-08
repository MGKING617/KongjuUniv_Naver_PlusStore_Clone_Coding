package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Customer;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void register(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer login(String loginId, String password) {
        return customerRepository.findByLoginId(loginId)

                .filter(c -> c.getPassword().equals(password))
                .orElse(null);
    }
}
package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.Seller;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    public void register(Seller seller) {
        sellerRepository.save(seller);
    }

    public Seller login(String loginId, String password) {
        return sellerRepository.findByLoginId(loginId)
                .filter(s -> s.getPassword().equals(password))
                .orElse(null);
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }
}
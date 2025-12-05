package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto.ProductDto;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    // 1. 전체 상품 목록 조회
    public List<ProductDto.Summary> getAllProducts() {
        return productRepository.findAllActiveProducts().stream()
                .map(product -> {
                    ProductDto.Summary summary = new ProductDto.Summary();
                    summary.setId(product.getId());
                    summary.setName(product.getName());
                    summary.setPrice(product.getPrice());
                    summary.setSellerName(product.getSeller().getStoreName());
                    return summary;
                })
                .collect(Collectors.toList());
    }

    // 2. 상품 상세 조회
    public ProductDto.Detail getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));

        List<ProductOption> options = productOptionRepository.findByProductId(productId);

        ProductDto.Detail detail = new ProductDto.Detail();
        detail.setId(product.getId());
        detail.setName(product.getName());
        detail.setPrice(product.getPrice());
        detail.setSellerName(product.getSeller().getStoreName());

        // 옵션 DTO 변환
        List<ProductDto.OptionDto> optionDtos = options.stream().map(opt -> {
            ProductDto.OptionDto dto = new ProductDto.OptionDto();
            dto.setId(opt.getId());
            dto.setOptionName(opt.getOptionName());
            dto.setExtraPrice(opt.getExtraPrice());
            dto.setStock(opt.getStock());
            return dto;
        }).collect(Collectors.toList());

        detail.setOptions(optionDtos);
        return detail;
    }
}
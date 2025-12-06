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
    private final ProductOptionRepository optionRepository;
    private final SellerRepository sellerRepository;

    // [1] 상품 등록 (판매자용) - Transactional 필수
    @Transactional
    public Long createProduct(ProductDto.CreateRequest req) {
        Seller seller = sellerRepository.findById(req.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));

        Product product = Product.builder()
                .seller(seller)
                .name(req.getName())
                .price(req.getPrice())
                .stock(req.getStock())
                .build();
        productRepository.save(product);

        // 옵션 저장
        if (req.getOptions() != null) {
            for (ProductDto.OptionCreateRequest optReq : req.getOptions()) {
                ProductOption option = ProductOption.builder()
                        .product(product)
                        .optionName(optReq.getOptionName())
                        .extraPrice(optReq.getExtraPrice())
                        .stock(optReq.getStock())
                        .build();
                optionRepository.save(option);
            }
        }
        return product.getProductId();
    }

    // [2] 전체 상품 목록 조회 (메인 페이지)
    public List<ProductDto.Summary> getAllProducts() {
        return productRepository.findAll().stream().map(p -> {
            ProductDto.Summary summary = new ProductDto.Summary();
            summary.setId(p.getProductId()); // 엔티티의 getProductId() 사용
            summary.setName(p.getName());
            summary.setPrice(p.getPrice());
            summary.setSellerName(p.getSeller().getStoreName());
            summary.setImg("https://via.placeholder.com/300"); // 임시 이미지
            return summary;
        }).collect(Collectors.toList());
    }

    // [3] 상품 상세 조회 (상세 페이지)
    public ProductDto.Detail getProductDetail(Long productId) {
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        ProductDto.Detail detail = new ProductDto.Detail();
        detail.setId(p.getProductId());
        detail.setName(p.getName());
        detail.setPrice(p.getPrice());
        detail.setSellerName(p.getSeller().getStoreName());
        detail.setImg("https://via.placeholder.com/400");

        // 옵션 변환
        List<ProductDto.OptionDto> options = p.getOptions().stream().map(opt -> {
            ProductDto.OptionDto optDto = new ProductDto.OptionDto();
            optDto.setId(opt.getOptionId()); // getOptionId() 사용
            optDto.setOptionName(opt.getOptionName());
            optDto.setExtraPrice(opt.getExtraPrice());
            optDto.setStock(opt.getStock());
            return optDto;
        }).collect(Collectors.toList());

        detail.setOptions(options);
        return detail;
    }
}
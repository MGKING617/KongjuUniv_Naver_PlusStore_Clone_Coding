package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    // 상품 목록용 (간단 정보)
    @Data
    public static class Summary {
        private Long id;
        private String sellerName; // 판매자 이름
        private String name;       // 상품명
        private BigDecimal price;  // 가격
    }

    // 상품 상세용 (자세한 정보 + 옵션 목록)
    @Data
    public static class Detail {
        private Long id;
        private String name;
        private BigDecimal price;
        private String sellerName;
        private List<OptionDto> options; // 옵션 리스트 포함
    }

    @Data
    public static class OptionDto {
        private Long id;
        private String optionName;
        private BigDecimal extraPrice;
        private int stock;
    }
}
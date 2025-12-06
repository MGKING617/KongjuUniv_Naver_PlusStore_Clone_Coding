package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    // [1] 상품 등록 요청 (판매자용)
    @Data
    public static class CreateRequest {
        private Long sellerId;     // 판매자 ID
        private String name;       // 상품명
        private BigDecimal price;  // 가격
        private Integer stock;     // 재고
        private List<OptionCreateRequest> options; // 옵션 리스트
    }

    @Data
    public static class OptionCreateRequest {
        private String optionName;     // 옵션명
        private BigDecimal extraPrice; // 추가금
        private Integer stock;         // 옵션 재고
    }

    // [2] 상품 목록 조회용 (메인 페이지)
    @Data
    public static class Summary {
        private Long id;            // 상품 ID (프론트에서 링크 걸 때 필요)
        private String sellerName;  // 판매자 가게명
        private String name;        // 상품명
        private BigDecimal price;   // 가격
        private String img;         // 이미지 URL (임시)
    }

    // [3] 상품 상세 조회용 (상세 페이지)
    @Data
    public static class Detail {
        private Long id;
        private String name;
        private BigDecimal price;
        private String sellerName;
        private String img;
        private List<OptionDto> options; // 옵션 목록 포함
    }

    @Data
    public static class OptionDto {
        private Long id;           // 옵션 ID (장바구니 담을 때 필요)
        private String optionName;
        private BigDecimal extraPrice;
        private int stock;
    }
}
package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;
import java.math.BigDecimal;

public class CartDto {

    // [1] 장바구니 담기 요청 (프론트 -> 백엔드)
    @Data
    public static class AddRequest {
        private Long customerId; // 누가
        private Long optionId;   // 어떤 옵션을
        private int quantity;    // 몇 개
    }

    // [2] 장바구니 조회 응답 (백엔드 -> 프론트)
    @Data
    public static class Response {
        private Long optionId;      // 옵션 ID (삭제/수정 시 필요)
        private String productName; // 상품명
        private String optionName;  // 옵션명
        private int quantity;       // 수량
        private BigDecimal price;   // 최종 가격 (상품가 + 옵션가)
    }
}
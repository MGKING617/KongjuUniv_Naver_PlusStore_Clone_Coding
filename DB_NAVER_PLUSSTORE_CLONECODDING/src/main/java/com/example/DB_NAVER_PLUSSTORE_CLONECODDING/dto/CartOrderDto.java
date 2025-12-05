package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;

public class CartOrderDto {

    // 장바구니 담기 요청
    @Data
    public static class CartAddRequest {
        private Long customerId; // 누가
        private Long optionId;   // 어떤 옵션을
        private int quantity;    // 몇 개
    }

    // 주문 생성 요청
    @Data
    public static class OrderCreateRequest {
        private Long customerId; // 누가 주문하는지
    }
}
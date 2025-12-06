package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {

    // [1] 주문 생성 요청
    @Data
    public static class CreateRequest {
        private Long customerId; // 누가 주문하는지
    }

    // [2] 주문 완료 응답 (주문번호 보여주기용)
    @Data
    public static class Response {
        private Long orderId;
        private BigDecimal totalAmount;
        private String status;
        private LocalDateTime orderedAt;
    }
}
package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {


    @Data
    public static class CreateRequest {
        private Long customerId;
    }


    @Data
    public static class Response {
        private Long orderId;
        private BigDecimal totalAmount;
        private String status;
        private LocalDateTime orderedAt;
    }
}
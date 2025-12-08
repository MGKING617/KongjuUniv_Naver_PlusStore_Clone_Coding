package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;
import java.math.BigDecimal;

public class CartDto {

    @Data
    public static class AddRequest {
        private Long customerId;
        private Long optionId;
        private int quantity;
    }

    @Data
    public static class Response {
        private Long optionId;
        private String productName;
        private String optionName;
        private int quantity;
        private BigDecimal price;
    }
}
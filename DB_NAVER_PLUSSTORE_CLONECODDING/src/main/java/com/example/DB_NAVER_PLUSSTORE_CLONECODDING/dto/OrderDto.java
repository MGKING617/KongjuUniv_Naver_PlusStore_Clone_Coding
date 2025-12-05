package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;

public class OrderDto {
    @Data
    public static class CreateRequest {
        private Long customerId;
    }
}
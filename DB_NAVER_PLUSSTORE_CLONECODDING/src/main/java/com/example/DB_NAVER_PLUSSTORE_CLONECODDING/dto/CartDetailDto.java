package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

@lombok.Data
public class CartDetailDto {
    private Long optionId;
    private String productName;
    private String optionName;
    private int quantity;
    private java.math.BigDecimal price;
}

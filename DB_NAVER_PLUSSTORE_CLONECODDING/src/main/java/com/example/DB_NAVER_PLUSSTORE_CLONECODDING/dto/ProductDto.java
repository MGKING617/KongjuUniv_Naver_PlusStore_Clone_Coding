package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    @Data
    public static class CreateRequest {
        private Long sellerId;
        private String name;
        private BigDecimal price;
        private Integer stock;
        private List<OptionCreateRequest> options;
    }

    @Data
    public static class OptionCreateRequest {
        private String optionName;
        private BigDecimal extraPrice;
        private Integer stock;
    }

    @Data
    public static class Summary {
        private Long id;
        private String sellerName;
        private String name;
        private BigDecimal price;
        private String img;
    }

    @Data
    public static class Detail {
        private Long id;
        private String name;
        private BigDecimal price;
        private String sellerName;
        private String img;
        private List<OptionDto> options;
    }

    @Data
    public static class OptionDto {
        private Long id;
        private String optionName;
        private BigDecimal extraPrice;
        private int stock;
    }
}
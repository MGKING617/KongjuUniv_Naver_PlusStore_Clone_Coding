package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryId implements Serializable {
    private Long productId;
    private Long categoryId;
}
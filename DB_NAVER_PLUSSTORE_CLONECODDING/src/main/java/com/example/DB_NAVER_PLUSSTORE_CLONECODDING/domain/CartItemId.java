package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartItemId implements Serializable {
    private Long customerId;
    private Long optionId;
}
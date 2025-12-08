package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderItemId implements Serializable {
    private Long orderId;
    private Integer lineNo;
}
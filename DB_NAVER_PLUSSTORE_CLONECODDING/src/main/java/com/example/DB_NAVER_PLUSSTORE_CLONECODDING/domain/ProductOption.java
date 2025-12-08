package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ProductOption")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "option_name", nullable = false, length = 200)
    private String optionName;

    @Column(name = "extra_price", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal extraPrice = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private Integer stock = 0;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE','DISABLED') DEFAULT 'ACTIVE'")
    @Builder.Default
    private OptionStatus status = OptionStatus.ACTIVE;

    public enum OptionStatus {
        ACTIVE, DISABLED
    }
}
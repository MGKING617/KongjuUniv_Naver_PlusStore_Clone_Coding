package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Customer")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;


    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
    private String address;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE','SUSPENDED','DELETED') DEFAULT 'ACTIVE'")
    @Builder.Default
    private CustomerStatus status = CustomerStatus.ACTIVE;

    public enum CustomerStatus { ACTIVE, SUSPENDED, DELETED }
}
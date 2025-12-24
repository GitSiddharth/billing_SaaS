package com.s1.billing.product;

import com.s1.billing.company.Company;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String name;

    private Double price;

    private Double gstPercentage;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
}

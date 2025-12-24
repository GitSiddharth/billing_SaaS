package com.s1.billing.customer;

import com.s1.billing.company.Company;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String name;

    private String email;

    private String phone;

    private String gstNumber;

    private String address;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
}

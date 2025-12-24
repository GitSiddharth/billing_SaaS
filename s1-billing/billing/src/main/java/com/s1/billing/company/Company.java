package com.s1.billing.company;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String gstNumber;

    private String address;

    private String state;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters (we’ll simplify later)
}

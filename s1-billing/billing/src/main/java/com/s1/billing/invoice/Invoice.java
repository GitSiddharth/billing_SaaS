package com.s1.billing.invoice;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.s1.billing.company.Company;
import com.s1.billing.customer.Customer;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(unique = true)
    private String invoiceNumber;

    private LocalDate invoiceDate;

    private Double subtotal;
    private Double cgst;
    private Double sgst;
    private Double igst;
    private Double total;

    @JsonManagedReference
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items;

    private LocalDateTime createdAt = LocalDateTime.now();





}

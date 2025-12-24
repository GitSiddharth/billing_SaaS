package com.s1.billing.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class InvoiceResponseDTO {

    public Long id;
    public String invoiceNumber;
    public LocalDate invoiceDate;

    public Double subtotal;
    public Double cgst;
    public Double sgst;
    public Double igst;
    public Double total;

    public CompanyDTO company;
    public CustomerDTO customer;
    public List<InvoiceItemDTO> items;



    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }



}

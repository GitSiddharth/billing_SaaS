package com.s1.billing.dto;

import lombok.Data;

@Data
public class InvoiceItemDTO {
    public String productName;
    public Double price;
    public Integer quantity;
    public Double gstPercentage;
    public Double total;
}

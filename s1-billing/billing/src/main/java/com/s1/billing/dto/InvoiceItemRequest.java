package com.s1.billing.dto;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InvoiceItemRequest {

    private Long productId;
    private Integer quantity;
}

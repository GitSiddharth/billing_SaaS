package com.s1.billing.dto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class InvoiceRequest {
    private Long companyId;
    private Long customerId;
    private List<InvoiceItemRequest> items;


}

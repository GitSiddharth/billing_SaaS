package com.s1.billing.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public String gstNumber;
}

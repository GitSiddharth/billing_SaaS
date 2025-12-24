package com.s1.billing.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private Long companyId;
    private String email;
    private String password;
    private String roles; // ADMIN / STAFF
}

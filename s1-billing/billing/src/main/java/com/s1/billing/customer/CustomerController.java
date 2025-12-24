package com.s1.billing.customer;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/company/{companyId}")
    public List<Customer> getByCompany(@PathVariable Long companyId) {
        return customerService.getCustomersByCompany(companyId);
    }
}

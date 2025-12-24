package com.s1.billing.product;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/company/{companyId}")
    public List<Product> getByCompany(@PathVariable Long companyId) {
        return productService.getProductsByCompany(companyId);
    }
}

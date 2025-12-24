package com.s1.billing.invoice;

import com.s1.billing.company.Company;
import com.s1.billing.company.CompanyRepository;
import com.s1.billing.customer.Customer;
import com.s1.billing.customer.CustomerRepository;
import com.s1.billing.dto.*;
import com.s1.billing.exception.ResourceNotFoundException;
import com.s1.billing.product.Product;
import com.s1.billing.product.ProductRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            ProductRepository productRepository,
            CompanyRepository companyRepository,
            CustomerRepository customerRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }



    public InvoiceResponseDTO createInvoice(InvoiceRequest request) {

        System.out.println("Creating invoice for companyId: " + request.getCompanyId());

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow();

        Invoice invoice = new Invoice();
        invoice.setCompany(company);
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());

        double subtotal = 0;
        double gstTotal = 0;

        List<InvoiceItem> items = new ArrayList<>();

        for (InvoiceItemRequest itemReq : request.getItems()) {

            Product product = productRepository.findById(itemReq.getProductId()).orElseThrow(() ->
                    new ResourceNotFoundException("Product not found with id: " + itemReq.getProductId())
            );


            double itemPrice = product.getPrice() * itemReq.getQuantity();
            double gstAmount = itemPrice * product.getGstPercentage() / 100;

            subtotal += itemPrice;
            gstTotal += gstAmount;

            InvoiceItem item = new InvoiceItem();
            item.setInvoice(invoice);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice());
            item.setGstPercentage(product.getGstPercentage());
            item.setTotal(itemPrice + gstAmount);

            items.add(item);
        }

        invoice.setSubtotal(subtotal);
        invoice.setCgst(gstTotal / 2);
        invoice.setSgst(gstTotal / 2);
        invoice.setIgst(0.0);
        invoice.setTotal(subtotal + gstTotal);
        invoice.setItems(items);

        Invoice saved = invoiceRepository.save(invoice);


        return mapToDTO(saved);
    }

    public InvoiceResponseDTO getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + invoiceId));
    }


    public List<InvoiceResponseDTO> getInvoicesByCompany(Long companyId) {
        return invoiceRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    private InvoiceResponseDTO mapToDTO(Invoice invoice) {

        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.id = invoice.getId();
        dto.invoiceNumber = invoice.getInvoiceNumber();
        dto.invoiceDate = invoice.getInvoiceDate();

        dto.subtotal = invoice.getSubtotal();
        dto.cgst = invoice.getCgst();
        dto.sgst = invoice.getSgst();
        dto.igst = invoice.getIgst();
        dto.total = invoice.getTotal();

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.id = invoice.getCompany().getId();
        companyDTO.name = invoice.getCompany().getName();
        companyDTO.gstNumber = invoice.getCompany().getGstNumber();
        companyDTO.state = invoice.getCompany().getState();
        dto.company = companyDTO;

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.id = invoice.getCustomer().getId();
        customerDTO.name = invoice.getCustomer().getName();
        customerDTO.email = invoice.getCustomer().getEmail();
        customerDTO.phone = invoice.getCustomer().getPhone();
        customerDTO.gstNumber = invoice.getCustomer().getGstNumber();
        dto.customer = customerDTO;

        dto.items = invoice.getItems().stream().map(item -> {
            InvoiceItemDTO i = new InvoiceItemDTO();
            i.productName = item.getProduct().getName();
            i.price = item.getPrice();
            i.quantity = item.getQuantity();
            i.gstPercentage = item.getGstPercentage();
            i.total = item.getTotal();
            return i;
        }).toList();

        return dto;
    }




}


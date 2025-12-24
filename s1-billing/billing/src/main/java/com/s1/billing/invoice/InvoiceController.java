package com.s1.billing.invoice;

import com.s1.billing.dto.InvoiceRequest;
import com.s1.billing.dto.InvoiceResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    private final InvoicePdfService invoicePdfService;


    public InvoiceController(InvoiceService invoiceService,InvoicePdfService invoicePdfService) {
        this.invoiceService = invoiceService;
        this.invoicePdfService = invoicePdfService;
    }

    @PostMapping
    public InvoiceResponseDTO createInvoice(@RequestBody InvoiceRequest request) {
        return invoiceService.createInvoice(request);
    }
    @GetMapping("/company/{companyId}")
    public List<InvoiceResponseDTO> getInvoicesByCompany(@PathVariable Long companyId) {
        return invoiceService.getInvoicesByCompany(companyId);
    }

    @GetMapping("/{invoiceId}")
    public InvoiceResponseDTO getInvoiceById(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id) {

        InvoiceResponseDTO invoice = invoiceService.getInvoiceById(id);
        byte[] pdf = invoicePdfService.generateInvoicePdf(invoice);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice-" + invoice.getInvoiceNumber() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


}

package com.s1.billing.invoice;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.s1.billing.dto.InvoiceResponseDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class InvoicePdfService {

    public byte[] generateInvoicePdf(InvoiceResponseDTO invoice) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);

            document.open();

            // ===== TITLE =====
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("TAX INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            // ===== COMPANY DETAILS =====
            document.add(new Paragraph("Company: " + invoice.getCompany().getName()));
            document.add(new Paragraph("GSTIN: " + invoice.getCompany().getGstNumber()));
            document.add(new Paragraph("State: " + invoice.getCompany().getState()));

            document.add(new Paragraph(" "));

            // ===== CUSTOMER DETAILS =====
            document.add(new Paragraph("Bill To:"));
            document.add(new Paragraph(invoice.getCustomer().getName()));
            document.add(new Paragraph("GSTIN: " + invoice.getCustomer().getGstNumber()));
            document.add(new Paragraph("Email: " + invoice.getCustomer().getEmail()));

            document.add(new Paragraph(" "));

            // ===== INVOICE INFO =====
            document.add(new Paragraph("Invoice No: " + invoice.getInvoiceNumber()));
            document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate()));

            document.add(new Paragraph(" "));

            // ===== ITEMS TABLE =====
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell("Product");
            table.addCell("Qty");
            table.addCell("Price");
            table.addCell("GST%");
            table.addCell("Total");

            invoice.getItems().forEach(item -> {
                table.addCell(item.getProductName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getPrice()));
                table.addCell(String.valueOf(item.getGstPercentage()));
                table.addCell(String.valueOf(item.getTotal()));
            });

            document.add(table);

            document.add(new Paragraph(" "));

            // ===== TOTALS =====
            document.add(new Paragraph("Subtotal: ₹" + invoice.getSubtotal()));
            document.add(new Paragraph("CGST: ₹" + invoice.getCgst()));
            document.add(new Paragraph("SGST: ₹" + invoice.getSgst()));
            document.add(new Paragraph("IGST: ₹" + invoice.getIgst()));

            Font totalFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            document.add(new Paragraph("TOTAL: ₹" + invoice.getTotal(), totalFont));

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating invoice PDF", e);
        }
    }
}

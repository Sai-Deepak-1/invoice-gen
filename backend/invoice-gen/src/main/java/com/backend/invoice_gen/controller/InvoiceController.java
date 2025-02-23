package com.backend.invoice_gen.controller;

import com.backend.invoice_gen.dto.InvoiceDTO;
import com.backend.invoice_gen.dto.InvoiceItemDTO;
import com.backend.invoice_gen.entity.Invoice;
import com.backend.invoice_gen.entity.InvoiceItem;
import com.backend.invoice_gen.entity.Product;
import com.backend.invoice_gen.repository.InvoiceRepository;
import com.backend.invoice_gen.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Invoice> getAllInvoices(){
        return invoiceRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceDTO invoiceDTO){
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        invoice.setCustomerName(invoiceDTO.getCustomerName());
        invoice.setInvoiceDate(invoiceDTO.getInvoiceDate());

        BigDecimal totalAmount = BigDecimal.ZERO;
        for(InvoiceItemDTO itemDTO : invoiceDTO.getItems()){
            Product product = productRepository.findById(itemDTO.getProductId()).orElse(null);
            if(product == null){
                return ResponseEntity.badRequest().build();
            }
            InvoiceItem item = new InvoiceItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(product.getPrice());
            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(itemDTO.getQuantity()));
            item.setTotalPrice(itemTotal);
            item.setInvoice(invoice);
            invoice.getItems().add(item);
            totalAmount = totalAmount.add(itemTotal);
        }
        invoice.setTotalAmount(totalAmount);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return ResponseEntity.ok(savedInvoice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id){
        return invoiceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO){
        return (ResponseEntity<Invoice>) invoiceRepository.findById(id).map(invoice -> {
            try {
                invoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
                invoice.setCustomerName(invoiceDTO.getCustomerName());
                invoice.setInvoiceDate(invoiceDTO.getInvoiceDate());
                // Clear existing items
                invoice.getItems().clear();
                BigDecimal totalAmount = BigDecimal.ZERO;
                for(InvoiceItemDTO itemDTO : invoiceDTO.getItems()){
                    Product product = productRepository.findById(itemDTO.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    InvoiceItem item = new InvoiceItem();
                    item.setProduct(product);
                    item.setQuantity(itemDTO.getQuantity());
                    item.setUnitPrice(product.getPrice());
                    BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(itemDTO.getQuantity()));
                    item.setTotalPrice(itemTotal);
                    item.setInvoice(invoice);
                    invoice.getItems().add(item);
                    totalAmount = totalAmount.add(itemTotal);
                }
                invoice.setTotalAmount(totalAmount);
                Invoice updatedInvoice = invoiceRepository.save(invoice);
                return ResponseEntity.ok(updatedInvoice);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.<Invoice>badRequest().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id){
        return invoiceRepository.findById(id).map(invoice -> {
            invoiceRepository.delete(invoice);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Advanced Search endpoint: by invoice number, customer name, and date range
    @GetMapping("/search")
    public List<Invoice> searchInvoices(
            @RequestParam(required = false) String invoiceNumber,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){

        List<Invoice> invoices = invoiceRepository.findAll();
        if(invoiceNumber != null && !invoiceNumber.isEmpty()){
            invoices.retainAll(invoiceRepository.findByInvoiceNumberContainingIgnoreCase(invoiceNumber));
        }
        if(customerName != null && !customerName.isEmpty()){
            invoices.retainAll(invoiceRepository.findByCustomerNameContainingIgnoreCase(customerName));
        }
        if(startDate != null || endDate != null){
            List<Invoice> dateInvoices = invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
            invoices.retainAll(dateInvoices);
        }
        return invoices;
    }
}

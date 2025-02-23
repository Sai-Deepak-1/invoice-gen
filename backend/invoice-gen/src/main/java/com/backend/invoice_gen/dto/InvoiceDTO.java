// backend/src/main/java/com/example/invoiceapp/dto/InvoiceDTO.java
package com.backend.invoice_gen.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceDTO {
    private String invoiceNumber;
    private String customerName;
    private LocalDate invoiceDate;
    private List<InvoiceItemDTO> items;
}

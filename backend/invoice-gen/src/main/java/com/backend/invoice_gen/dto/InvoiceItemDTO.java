// backend/src/main/java/com/example/invoiceapp/dto/InvoiceItemDTO.java
package com.backend.invoice_gen.dto;

import lombok.Data;

@Data
public class InvoiceItemDTO {
    private Long productId;
    private Integer quantity;
}

package com.backend.invoice_gen.repository;

import com.backend.invoice_gen.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByInvoiceNumberContainingIgnoreCase(String invoiceNumber);
    List<Invoice> findByCustomerNameContainingIgnoreCase(String customerName);

    @Query("SELECT i FROM Invoice i WHERE (:startDate IS NULL OR i.invoiceDate >= :startDate) AND (:endDate IS NULL OR i.invoiceDate <= :endDate)")
    List<Invoice> findByInvoiceDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

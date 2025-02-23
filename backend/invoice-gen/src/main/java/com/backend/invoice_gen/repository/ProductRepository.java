package com.backend.invoice_gen.repository;

import com.backend.invoice_gen.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

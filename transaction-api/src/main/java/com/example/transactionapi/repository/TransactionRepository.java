package com.example.transactionapi.repository;

import com.example.transactionapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTransactionDateBetween(LocalDate from, LocalDate to);
    List<Transaction> findByCategoryIgnoreCase(String category);
    List<Transaction> findByTransactionDateBetweenAndCategoryIgnoreCase(LocalDate from, LocalDate to, String category);
}
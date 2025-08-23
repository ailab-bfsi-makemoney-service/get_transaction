package com.example.transactionapi.controller;

import com.example.transactionapi.model.Transaction;
import com.example.transactionapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @GetMapping
    public List<Transaction> getTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String category) {

        if (from != null && to != null && category != null) {
            return repository.findByTransactionDateBetweenAndCategoryIgnoreCase(from, to, category);
        } else if (from != null && to != null) {
            return repository.findByTransactionDateBetween(from, to);
        } else if (category != null) {
            return repository.findByCategoryIgnoreCase(category);
        } else {
            return repository.findAll();
        }
    }
}
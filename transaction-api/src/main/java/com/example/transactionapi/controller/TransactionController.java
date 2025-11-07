package com.example.transactionapi.controller;

import com.example.transactionapi.model.Transaction;
import com.example.transactionapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Map<String, Object>> getEnrichedTransactions(
            @RequestParam(required = false) String category) {

        StringBuilder sql = new StringBuilder("""
            SELECT 
                t.id,
                t.transaction_date AS "transactionDate",
                t.post_date AS "postDate",
                t.description,
                t.category,
                t.type,
                t.amount,
                t.memo,
                t.source_file AS "sourceFile",
                t.merchant_id AS "merchantId",
                m.merchant_name AS "merchantName",
                m.normalized_name AS "normalizedName",
                -- Extract restaurant_type titles from JSONB array
                CASE 
                    WHEN jsonb_typeof(m.merchant_type) = 'array' THEN
                        (
                            SELECT string_agg(elem->>'title', ', ')
                            FROM jsonb_array_elements(m.merchant_type) AS elem
                        )
                    ELSE NULL
                END AS "restaurantType",
                m.merchant_address AS "merchantAddress",
                m.google_types AS "googleTypes",
                m.rating AS "merchantRating",
                m.enrichment_status AS "enrichmentStatus",
                m.created_at AS "merchantCreatedAt"
            FROM bian.cc_transaction t
            LEFT JOIN bian.merchants m ON t.merchant_id = m.id
        """);

        if (category != null && !category.isBlank()) {
            sql.append(" WHERE LOWER(t.category) = LOWER(?)");
            return jdbcTemplate.queryForList(sql.toString(), category);
        } else {
            return jdbcTemplate.queryForList(sql.toString());
        }
    }
}

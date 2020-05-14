package com;

import lombok.Data;

import java.util.UUID;

@Data
public class Invoice {

    private String id, pdfUrl, userId;

    private Integer amount;

    public Invoice() {
    }

    public Invoice(String userId, Integer amount, String pdfUrl) { //
        this.id = UUID.randomUUID().toString();
        this.pdfUrl = pdfUrl;
        this.userId = userId;
        this.amount = amount;
    }

}
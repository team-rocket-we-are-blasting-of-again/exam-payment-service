package com.teamrocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponse {
    private String paymentId = UUID.randomUUID().toString();
    private int orderId;
    private double amount;
    private String message;
    private HttpStatus status;

    public PaymentResponse(String message, int orderId, double amount, HttpStatus status) {
        this.message = message;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }
}

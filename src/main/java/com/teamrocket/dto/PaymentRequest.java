package com.teamrocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentRequest {
    private int orderId;
    private double amount;

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                '}';
    }
}

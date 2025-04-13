package com.example.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitiatePaymentDto {
    Long amount;
    String orderId;
    String phoneNumber;
    String name;
    String email;
}

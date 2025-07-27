package com.example.paymentservice.dtos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class InitiatePaymentDtoTest {

    @Test
    void testGettersAndSetters() {
        InitiatePaymentDto dto = new InitiatePaymentDto();
        dto.setAmount(100L);
        dto.setOrderId("ORD123");
        dto.setPhoneNumber("9876543210");
        dto.setName("Alice");
        dto.setEmail("alice@example.com");

        assertEquals(100L, dto.getAmount());
        assertEquals("ORD123", dto.getOrderId());
        assertEquals("9876543210", dto.getPhoneNumber());
        assertEquals("Alice", dto.getName());
        assertEquals("alice@example.com", dto.getEmail());
    }
}
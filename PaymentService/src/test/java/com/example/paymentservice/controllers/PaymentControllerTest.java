package com.example.paymentservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.paymentservice.dtos.InitiatePaymentDto;
import com.example.paymentservice.services.IPaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testInitiatePayment() throws Exception {
        InitiatePaymentDto dto = new InitiatePaymentDto();
        dto.setAmount(100L);
        dto.setOrderId("ORD123");
        dto.setPhoneNumber("1234567890");
        dto.setName("John Doe");
        dto.setEmail("john@example.com");

        String expectedLink = "http://payment.link/ORD123";
        when(paymentService.getPaymentLink(100L, "ORD123", "1234567890", "John Doe", "john@example.com"))
                .thenReturn(expectedLink);

        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedLink));
    }
}
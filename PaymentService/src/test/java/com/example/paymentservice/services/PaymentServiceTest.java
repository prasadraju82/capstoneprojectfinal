package com.example.paymentservice.services;

import com.example.paymentservice.paymentgateways.IPaymentGateway;
import com.example.paymentservice.paymentgateways.PaymentGatewayChooserStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;
    private IPaymentGateway paymentGateway;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentGatewayChooserStrategy = mock(PaymentGatewayChooserStrategy.class);
        paymentGateway = mock(IPaymentGateway.class);
        paymentService = new PaymentService();
        // Use reflection to inject the mock (since field is package-private)
        try {
            var field = PaymentService.class.getDeclaredField("paymentGatewayChooserStrategy");
            field.setAccessible(true);
            field.set(paymentService, paymentGatewayChooserStrategy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetPaymentLink() {
        Long amount = 100L;
        String orderId = "ORD123";
        String phoneNumber = "1234567890";
        String name = "John Doe";
        String email = "john@example.com";
        String expectedLink = "http://payment.link/ORD123";

        when(paymentGatewayChooserStrategy.getBestPaymentGateway()).thenReturn(paymentGateway);
        when(paymentGateway.createStandardPaymentLink(amount, orderId, phoneNumber, name, email)).thenReturn(expectedLink);

        String result = paymentService.getPaymentLink(amount, orderId, phoneNumber, name, email);

        assertEquals(expectedLink, result);
        verify(paymentGatewayChooserStrategy).getBestPaymentGateway();
        verify(paymentGateway).createStandardPaymentLink(amount, orderId, phoneNumber, name, email);
    }
}
package com.example.paymentservice.paymentgateways;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RazorpayPaymentGatewayTest {

    private RazorpayClient razorpayClient;
    private RazorpayPaymentGateway razorpayPaymentGateway;

    @BeforeEach
    void setUp() throws Exception {
        razorpayClient = mock(RazorpayClient.class);
        razorpayPaymentGateway = new RazorpayPaymentGateway();

        // Mock PaymentLinkClient and inject into razorpayClient
        com.razorpay.PaymentLinkClient paymentLinkClient = mock(com.razorpay.PaymentLinkClient.class);
        var paymentLinkField = RazorpayClient.class.getDeclaredField("paymentLink");
        paymentLinkField.setAccessible(true);
        paymentLinkField.set(razorpayClient, paymentLinkClient);

        // Inject mock RazorpayClient into RazorpayPaymentGateway
        var field = RazorpayPaymentGateway.class.getDeclaredField("razorpayClient");
        field.setAccessible(true);
        field.set(razorpayPaymentGateway, razorpayClient);

        // Mock PaymentLink and its get("short_url") method
        PaymentLink mockPaymentLink = mock(PaymentLink.class);
        when(mockPaymentLink.get("short_url")).thenReturn("https://mocked.razorpay.link");
        when(paymentLinkClient.create(any(JSONObject.class))).thenReturn(mockPaymentLink);
    }

    @Test
    void testCreateStandardPaymentLink_ReturnsExpectedUrl() throws Exception {
        // Mock PaymentLink and its get("short_url") method
        PaymentLink mockPaymentLink = mock(PaymentLink.class);
        when(mockPaymentLink.get("short_url")).thenReturn("https://mocked.razorpay.link");

        // Mock razorpayClient.paymentLink.create to return mockPaymentLink
        var paymentLinkApi = mock(com.razorpay.PaymentLink.class, RETURNS_DEEP_STUBS);
        when(razorpayClient.paymentLink.create(any(JSONObject.class))).thenReturn(mockPaymentLink);

        Long amount = 100L;
        String orderId = "ORD123";
        String phoneNumber = "1234567890";
        String name = "John Doe";
        String email = "john@example.com";

        String result = razorpayPaymentGateway.createStandardPaymentLink(amount, orderId, phoneNumber, name, email);

        assertEquals("https://mocked.razorpay.link", result);
    }
}

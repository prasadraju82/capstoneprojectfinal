package com.example.paymentservice.paymentgateways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentGatewayChooserStrategyTest {

    private RazorpayPaymentGateway razorpayPaymentGateway;
    private StripePaymentGateway stripePaymentGateway;
    private PaymentGatewayChooserStrategy chooserStrategy;

    @BeforeEach
    void setUp() throws Exception {
        razorpayPaymentGateway = mock(RazorpayPaymentGateway.class);
        stripePaymentGateway = mock(StripePaymentGateway.class);
        chooserStrategy = new PaymentGatewayChooserStrategy();

        // Inject mocks using reflection since fields are package-private
        var razorpayField = PaymentGatewayChooserStrategy.class.getDeclaredField("razorpayPaymentGateway");
        razorpayField.setAccessible(true);
        razorpayField.set(chooserStrategy, razorpayPaymentGateway);

        var stripeField = PaymentGatewayChooserStrategy.class.getDeclaredField("stripePaymentGateway");
        stripeField.setAccessible(true);
        stripeField.set(chooserStrategy, stripePaymentGateway);
    }

    @Test
    void testGetBestPaymentGateway_ReturnsStripe() {
        IPaymentGateway result = chooserStrategy.getBestPaymentGateway();
        assertSame(stripePaymentGateway, result, "Should return StripePaymentGateway");
    }
}
package com.example.paymentservice.paymentgateways;

import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StripePaymentGatewayTest {

    private StripePaymentGateway stripePaymentGateway;

    @BeforeEach
    void setUp() {
        stripePaymentGateway = Mockito.spy(new StripePaymentGateway());
        // Inject a fake API key to avoid null pointer
        try {
            var field = StripePaymentGateway.class.getDeclaredField("stripeApiKey");
            field.setAccessible(true);
            field.set(stripePaymentGateway, "sk_test_fake");
        } catch (Exception e) {
            fail("Failed to set API key: " + e.getMessage());
        }
    }

    @Test
    void testCreateStandardPaymentLink_ReturnsExpectedUrl() throws Exception {
        // Mock Price
        Price mockPrice = mock(Price.class);
        when(mockPrice.getId()).thenReturn("price_123");
        doReturn(mockPrice).when(stripePaymentGateway).getPrice(anyLong());

        // Mock PaymentLink
        PaymentLink mockPaymentLink = mock(PaymentLink.class);
        when(mockPaymentLink.getUrl()).thenReturn("https://mocked.stripe.link");

        // Mock static PaymentLink.create
        try (MockedStatic<PaymentLink> mockedStatic = Mockito.mockStatic(PaymentLink.class)) {
            mockedStatic.when(() -> PaymentLink.create(any(PaymentLinkCreateParams.class)))
                    .thenReturn(mockPaymentLink);

            String url = stripePaymentGateway.createStandardPaymentLink(
                    100L, "ORD1", "1234567890", "Test User", "test@example.com"
            );

            assertEquals("https://mocked.stripe.link", url);
        }
    }

    @Test
    void testGetPrice_ReturnsMockedPrice() throws Exception {
        Price mockPrice = mock(Price.class);
        when(mockPrice.getId()).thenReturn("mocked_price_id");

        try (MockedStatic<Price> mockedStatic = mockStatic(Price.class)) {
            mockedStatic.when(() -> Price.create(any(PriceCreateParams.class)))
                    .thenReturn(mockPrice);

            Price result = stripePaymentGateway.getPrice(500L);

            assertNotNull(result);
            assertEquals("mocked_price_id", result.getId());
        }
    }
}


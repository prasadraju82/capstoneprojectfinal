package com.example.paymentservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RazorpayConfigTest {

    @Autowired
    private RazorpayConfig razorpayConfig;

    @Test
    void contextLoads() {
        assertNotNull(razorpayConfig, "RazorpayConfig should be loaded in the context");
    }
}
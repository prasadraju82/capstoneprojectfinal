package com.example.paymentservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebhookController.class)
class WebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRespondToWebhook() throws Exception {
        String sampleEvent = "{\"id\":\"evt_test_webhook\",\"object\":\"event\"}";

        mockMvc.perform(post("/stripeWebhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleEvent))
                .andExpect(status().isOk());
    }
}
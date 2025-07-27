package com.example.emailservice.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.emailservice.dtos.EmailDto;
import com.example.emailservice.utils.EmailUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerEmailClientTest {
    @InjectMocks
    private KafkaConsumerEmailClient kafkaEmailConsumer;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private EmailUtil emailUtilMock;


    @Test
    void testSendEmail_KafkaMessageDeserializationAndSend() throws Exception {
        // Arrange
        EmailDto dto = new EmailDto();
        dto.setFrom("sender@example.com");
        dto.setTo("recipient@example.com");
        dto.setSubject("Test Subject");
        dto.setBody("Hello, this is a test!");

        String messageJson = new ObjectMapper().writeValueAsString(dto);

        // Mock static method
        try (MockedStatic<EmailUtil> mockedEmailUtil = mockStatic(EmailUtil.class)) {

            // Act
            kafkaEmailConsumer.sendEmail(messageJson);

            // Assert
            mockedEmailUtil.verify(() ->
                            EmailUtil.sendEmail(any(Session.class), eq(dto.getTo()), eq(dto.getSubject()), eq(dto.getBody())),
                    times(1)
            );
        }
    }
}
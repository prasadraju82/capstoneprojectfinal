package com.example.emailservice.utils;

import com.example.emailservice.clients.KafkaConsumerEmailClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailUtilTest {
    @Test
    void testSendEmailSuccess() throws Exception {
        // Arrange
        Session session = Session.getInstance(new Properties());

        MimeMessage mimeMessage = spy(new MimeMessage(session));
        // Mock Transport static method
        try (MockedStatic<Transport> mockedTransport = mockStatic(Transport.class)) {

            // Act
            EmailUtil.sendEmail(session, "to@example.com", "Test Subject", "Test Body");

            // Assert
            mockedTransport.verify(() -> Transport.send(any(MimeMessage.class)), times(1));
        }
    }

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
    }


    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }
}
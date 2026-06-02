package com.email.consumer;

import com.email.service.EmailSenderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RegistrationConsumer {

    private final EmailSenderService emailSenderService;

    public RegistrationConsumer(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @KafkaListener(topics = "registration-success", groupId = "email-service-group")
    public void consume(String message) {

        String[] data = message.split(",");

        String email = data[0];
        String name = data[1];

        emailSenderService.sendRegistrationEmail(email, name);

        System.out.println("Registration email sent to: " + email);
    }
}
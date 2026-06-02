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
    try {
        System.out.println("Received Kafka message: " + message);

        String[] data = message.split(",");
        String email = data[0];
        String name = data.length > 1 ? data[1] : "User";

        emailSenderService.sendRegistrationEmail(email, name);

        System.out.println("Registration email sent to: " + email);

    } catch (Exception e) {
        System.out.println("Email sending failed: " + e.getMessage());
        e.printStackTrace();
    }
}
    
}
package com.example.notification.kafka;

import com.example.notification.service.NotificationService;
import patient.events.PatientEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final NotificationService notificationService;


    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @KafkaListener(topics = "patient", groupId = "notification-service")
    public void consume(ConsumerRecord<String, byte[]> record) {
        try {
            // Deserialize Protobuf message
            PatientEvent event = PatientEvent.parseFrom(record.value());

            // Build email content
            String subject = "New Patient Event: " + event.getEventType();
            String body = "Patient ID: " + event.getPatientId() + "\n" +
                    "Name: " + event.getName() + "\n" +
                    "Email: " + event.getEmail() + "\n" +
                    "Event Type: " + event.getEventType();

            // Send email
            notificationService.sendSimpleEmail(event.getEmail(), subject, body);

        } catch (InvalidProtocolBufferException e) {
            System.err.println("Failed to deserialize protobuf message: " + e.getMessage());
        }
    }


}

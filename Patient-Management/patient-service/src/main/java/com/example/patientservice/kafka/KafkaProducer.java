package com.example.patientservice.kafka;


import com.example.patientservice.dto.PatientAuth;
import com.example.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;
import patient.patientAdd.PatientAddition;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(
            KafkaProducer.class);

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try {
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", event);
        }
    }


    public void sendPatient(PatientAuth patient) {
        PatientAddition event = PatientAddition.newBuilder()
                .setEmail(patient.getEmail())
                .setPassword(patient.getPassword())
                .build();

        try {
            kafkaTemplate.send("patientAdd", event.toByteArray());
            log.info("✅ Sent to the Kafka");
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", event);
        }
    }


}

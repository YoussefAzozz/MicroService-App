package com.example.authservice.kafka;

import com.example.authservice.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import patient.patientAdd.PatientAddition;
import com.google.protobuf.InvalidProtocolBufferException;

import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class KafkaConsumer {

    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(
            KafkaConsumer.class);
    private final PasswordEncoder passwordEncoder;

    public KafkaConsumer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @KafkaListener(topics = "patientAdd", groupId = "auth-service")
    public void consumeEvent(byte[] event) {

        try {
            PatientAddition patientEvent = PatientAddition.parseFrom(event);
            // ... perform any business related to analytics here


            Optional<User> existingUser = userRepository.findByEmail(patientEvent.getEmail());

            if (existingUser.isPresent()) {
                log.info("User already exists with email: {}", patientEvent.getEmail());
            } else {
                // Create new user
                User newUser = new User();
                newUser.setEmail(patientEvent.getEmail());
                String hashedPassword = passwordEncoder.encode(patientEvent.getPassword());
                newUser.setPassword(hashedPassword);
                newUser.setRole(Role.PATIENT);
                userRepository.save(newUser);
                log.debug("New user has been saved with email: {}", newUser.getEmail());

            }
        } catch (InvalidProtocolBufferException e) {
            log.error("Failed to deserialize protobuf message: {}", e.getMessage());
        }
    }
}

package com.example.patientservice.gRPC;
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;


@Service
public class BillingServiceGrpcClient {


    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;
    private final PatientRepository patientRepository;
    private static final Logger log = LoggerFactory.getLogger(
            BillingServiceGrpcClient.class);

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort,
            PatientRepository patientRepository) {

        this.patientRepository = patientRepository;

        log.info("Connecting to Billing Service GRPC service at {}:{}", serverAddress, serverPort);


        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);

    }


    public BillingResponse createBillingAccount( String name,String email) {

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found: " + email));


        BillingRequest request = BillingRequest.newBuilder().setUserId(patient.getId().toString())
                .setName(name).setEmail(email).build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response from billing service via GRPC: {}", response);
        return response;
    }


}

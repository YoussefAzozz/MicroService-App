package com.example.patientservice.controller;

import billing.BillingResponse;
import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.dto.RpcDTO;
import com.example.patientservice.dto.validators.CreatePatientValidationGroup;
import com.example.patientservice.gRPC.BillingServiceGrpcClient;
import com.example.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient" , description = "API for managing Patients")
public class PatientController {

    private final PatientService patientService;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    public PatientController(PatientService patientService,BillingServiceGrpcClient billingServiceGrpcClient) {
        this.patientService = patientService;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
    }


    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO)
    {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PostMapping("/create-patient-bill")
    @Operation(summary = "Create a new Patient Bill")
    public ResponseEntity<BillingResponse> createPatientBilling(@Validated @RequestBody RpcDTO rpcDTO) {

        BillingResponse billingResponse = billingServiceGrpcClient.createBillingAccount(
                rpcDTO.getName(), rpcDTO.getEmail());

        // Let Spring automatically convert BillingResponse -> JSON
        return ResponseEntity.ok(billingResponse);



    }

    @PutMapping("/update-patient/{id}")
    @Operation(summary = "Update a new Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDTO dto)
    {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,dto);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/delete-patient/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();

    }
}

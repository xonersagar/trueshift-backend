package com.trueshift.controller;

import com.trueshift.entity.VerificationRequest; // <--- CHANGE 1
import com.trueshift.repository.VerificationRequestRepository; // <--- CHANGE 2
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/verify")
@CrossOrigin(origins = "*")
public class VerificationController {

    @Autowired
    private VerificationRequestRepository verificationRequestRepository;


    @PostMapping("/request")
    public ResponseEntity<?> requestVerification(@RequestBody VerificationRequest verificationRequest) {
        verificationRequest.setStatus("PENDING");
        verificationRequestRepository.save(verificationRequest);
        return ResponseEntity.ok("Verification request submitted!");
    }

    // Get verification by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<VerificationRequest> verification = verificationRequestRepository.findById(id);
        return verification.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Request not found"));
    }

    // Get all verifications
    @GetMapping("/all")
    public List<VerificationRequest> getAll() {
        return verificationRequestRepository.findAll();
    }
}
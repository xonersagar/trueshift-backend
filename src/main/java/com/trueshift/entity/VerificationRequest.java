package com.trueshift.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VerificationRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "requested_by_company")
    private Company requestedByCompany;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Instant requestDate;
    private String status; // PENDING / APPROVED / REJECTED
    private Boolean employeeConsent;
}

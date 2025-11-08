package com.trueshift.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RedFlag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flagId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(columnDefinition = "TEXT")
    private String reason;

    private Instant flagDate;
    private String evidenceLink;
}

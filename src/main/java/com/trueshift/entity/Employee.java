package com.trueshift.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(unique = true)
    private String trustCode;

    private String fullName;
    private LocalDate dob;
    private String email;
    private String aadharNo;
    private String phone;


    private String password;


    private String otp;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean verified;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmploymentHistory> histories;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<RedFlag> redFlags;
}
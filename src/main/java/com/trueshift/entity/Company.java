package com.trueshift.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    private String name;


    private String email;

    private String trustCode;
    private String industry;
    private String location;
    private String registrationNo;

    @OneToMany(mappedBy = "company")
    private List<UserAccount> users;
}
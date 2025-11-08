package com.trueshift.controller;

import com.trueshift.entity.Company;
import com.trueshift.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    // Register new company
    @PostMapping("/register")
    public ResponseEntity<?> registerCompany(@RequestBody Company company) {
        Optional<Company> existing = companyRepository.findByEmail(company.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company already exists");
        }

        company.setTrustCode(UUID.randomUUID().toString());
        companyRepository.save(company);

        return ResponseEntity.ok(company);
    }

    // Get company by Trust Code
    @GetMapping("/trust/{code}")
    public ResponseEntity<?> getByTrustCode(@PathVariable String code) {
        Optional<Company> company = companyRepository.findByTrustCode(code);
        return company.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found"));
    }

    // List all companies
    @GetMapping("/all")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}

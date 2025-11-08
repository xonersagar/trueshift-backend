package com.trueshift.controller;

import com.trueshift.entity.Employee;
import com.trueshift.repository.EmployeeRepository;
import com.trueshift.service.EmailService;
import com.trueshift.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- NEW IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // REGISTER (send OTP)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee employee) {
        Optional<Employee> existing = employeeRepository.findByEmail(employee.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        employee.setOtp(otp);
        employee.setVerified(false);
        employeeRepository.save(employee);

        // Send OTP via email
        emailService.sendEmail(employee.getEmail(), "Your OTP Code", "Your OTP is: " + otp);

        return ResponseEntity.ok("OTP sent successfully!");
    }

    // VERIFY OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {

        Optional<Employee> empOpt = employeeRepository.findByEmail(email);
        if (empOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Employee emp = empOpt.get();
        if (emp.getOtp().equals(otp)) {
            emp.setVerified(true);
            emp.setOtp(null);
            employeeRepository.save(emp);
            return ResponseEntity.ok("OTP verified successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<Employee> empOpt = employeeRepository.findByEmail(email);
        if (empOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Employee emp = empOpt.get();
        if (!emp.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account not verified");
        }


        if (!passwordEncoder.matches(password, emp.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtService.generateToken(email);
        return ResponseEntity.ok(token);
    }
}
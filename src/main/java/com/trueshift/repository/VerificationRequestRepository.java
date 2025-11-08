package com.trueshift.repository;

import com.trueshift.entity.Employee;
import com.trueshift.entity.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {
    Optional<VerificationRequest> findByEmployee_TrustCode(String trustCode);

}

package com.trueshift.repository;

import com.trueshift.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByTrustCode(String trustCode);

    Optional<Employee> findByEmail(String email);
}
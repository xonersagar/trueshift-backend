package com.trueshift.service;

import com.trueshift.entity.Employee;
import com.trueshift.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee create(Employee emp) {
        return repo.save(emp);
    }

    public Optional<Employee> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<Employee> findByTrustCode(String trustCode) {
        return repo.findByTrustCode(trustCode);
    }
}

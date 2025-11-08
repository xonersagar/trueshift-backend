package com.trueshift.config;

import com.trueshift.entity.Employee;
import com.trueshift.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isEmpty()) {
            throw new UsernameNotFoundException("Employee not found with email: " + email);
        }

        Employee emp = employee.get();
        return new org.springframework.security.core.userdetails.User(
                emp.getEmail(),
                emp.getPassword(),
                new ArrayList<>()
        );
    }
}
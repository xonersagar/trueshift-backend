package com.trueshift.repository;


import com.trueshift.entity.EmploymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmploymentHistoryRepository extends JpaRepository<EmploymentHistory, Long> {
    Optional<EmploymentHistory> findByEmployee_TrustCode(String trustCode);
}

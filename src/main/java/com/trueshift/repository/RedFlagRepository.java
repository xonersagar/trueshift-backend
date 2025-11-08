package com.trueshift.repository;


import com.trueshift.entity.RedFlag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RedFlagRepository extends JpaRepository<RedFlag, Long> {
    Optional<RedFlag> findByEmployee_TrustCode(String trustCode);
}

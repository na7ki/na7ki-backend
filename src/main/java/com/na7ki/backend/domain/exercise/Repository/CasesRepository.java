package com.na7ki.backend.domain.exercise.Repository;

import com.na7ki.backend.domain.exercise.Entity.Cases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasesRepository extends JpaRepository<Cases, Long> {

    List<Cases> findByUserId(Long userId);
}

package com.na7ki.backend.domain.exercise.Repository;

import com.na7ki.backend.domain.exercise.Entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}

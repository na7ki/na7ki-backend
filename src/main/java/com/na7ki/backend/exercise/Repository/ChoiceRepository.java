package com.na7ki.backend.exercise.Repository;

import com.na7ki.backend.exercise.Entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}

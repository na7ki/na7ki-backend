package com.na7ki.backend.domain.exercise.Repository;

import com.na7ki.backend.domain.exercise.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByPkgIdOrderByOrderIndex(Long packageId);
}

package com.na7ki.backend.domain.exercise.Repository;

import com.na7ki.backend.domain.exercise.entity.Sound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundRepository extends JpaRepository<Sound, Long> {
}

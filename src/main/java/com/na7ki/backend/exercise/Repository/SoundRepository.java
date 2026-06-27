package com.na7ki.backend.exercise.Repository;

import com.na7ki.backend.exercise.Entity.Sound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundRepository extends JpaRepository<Sound, Long> {
}

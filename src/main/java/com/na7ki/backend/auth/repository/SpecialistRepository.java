package com.na7ki.backend.auth.repository;

import com.na7ki.backend.auth.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    @Query("SELECT COUNT(s) > 0 FROM Specialist s JOIN s.personalImages_paths p WHERE p IN :paths")
    boolean existsByAnyPersonalImage(@Param("paths") List<String> paths);

}

package com.na7ki.backend.domain.exercise.Repository;

import com.na7ki.backend.domain.exercise.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    List<UserSession> findByUserId(Long userId);
    List<UserSession> findByUserIdAndPkgId(Long userId, Long packageId);
}

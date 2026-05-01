package com.na7ki.backend.auth.repository;

import com.na7ki.backend.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE TYPE(u) = :type")
    long countByType(@Param("type") Class<?> type);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}

package com.na7ki.backend.auth;

import com.na7ki.backend.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);



    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u JOIN u.phoneNumbers p WHERE p IN :phoneNumbers")
    boolean existsByAnyPhoneNumber(@Param("phoneNumbers") List<String> phoneNumbers);

}

package com.na7ki.backend.auth.verificationcode;

import com.na7ki.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table (name = "verification_codes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, updatable = false, length = 4)
        private String fourDigitCode;

        @Column(nullable = false, updatable = false)
        private LocalDateTime expiresAt;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

}

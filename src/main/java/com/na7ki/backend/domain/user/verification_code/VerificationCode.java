package com.na7ki.backend.domain.user.verification_code;

import com.na7ki.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
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

        @Column(nullable = false)
        private LocalDateTime expiresAt;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false, updatable = false)
        private User user;





        public boolean isExpired () {
                return expiresAt.isBefore(LocalDateTime.now());
        }

}

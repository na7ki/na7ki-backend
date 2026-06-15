package com.na7ki.backend.domain.user;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.na7ki.backend.account_management.dto.UpdateProfileRequest;
import com.na7ki.backend.domain.user.exception.EmailNotAssociatedWithAnyAccountException;
import com.na7ki.backend.auth.exception.UnknownRoleException;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.repository.PatientRepository;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final PatientRepository patientRepository;

    private final ObjectMapper objectMapper;



    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }

    public boolean isPhoneNumberUnique(String phoneNumber) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }

    public User findByEmailOrThrow (String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotAssociatedWithAnyAccountException("No account is associated with this email"));
    }

    public Optional<User> findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        switch (user) {
            case Specialist specialist -> specialistRepository.save(specialist);
            case Patient patient       -> patientRepository.save(patient);
            default                    -> throw new UnknownRoleException("Unknown user type: " + user.getClass().getSimpleName());
        }
    }

    public void updateUser (String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotAssociatedWithAnyAccountException("No user is associated with this email"));

        try {
            objectMapper.updateValue(user, request);
        } catch (JsonMappingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid profile data");
        }

        this.saveUser(user);
    }

    public Long createSpecialistIdNumberPart() {
        return userRepository.countByType(Specialist.class) + 1;
    }
}

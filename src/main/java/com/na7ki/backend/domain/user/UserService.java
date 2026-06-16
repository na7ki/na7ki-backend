package com.na7ki.backend.domain.user;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final PatientRepository patientRepository;



    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }

    public boolean isPhoneNumberUnique(String phoneNumber) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }

    public Optional<User> findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmailOrThrow (String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotAssociatedWithAnyAccountException("No account is associated with this email"));
    }

    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
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

        //changing the email will change the identifier of the user, so it must be handled carefully
        if (request.getEmail().isPresent())
        {
            user.setEmail(request.getEmail().get());

        }

        request.getName().ifPresent(user::setName);
        request.getPhoneNumber().ifPresent(user::setPhoneNumber);
        request.getGender().ifPresent(user::setGender);

        //Specialist fields
        request.getAddress().ifPresent(((Specialist)user)::setAddress);
        request.getDateOfBirth().ifPresent(((Specialist)user)::setDateOfBirth);
        request.getDisplayImage_path().ifPresent(((Specialist)user)::setDisplayImage_path);

        this.saveUser(user);
    }

    public Long createSpecialistIdNumberPart() {
        return userRepository.countByType(Specialist.class) + 1;
    }
}

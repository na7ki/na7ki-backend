package com.na7ki.backend.domain.user;

import com.na7ki.backend.account_management.dto.UpdateProfileRequest;
import com.na7ki.backend.domain.user.exception.EmailNotUniqueException;
import com.na7ki.backend.domain.user.exception.PhoneNumberNotUniqueException;
import com.na7ki.backend.domain.user.exception.EmailNotAssociatedWithAnyAccountException;
import com.na7ki.backend.domain.user.exception.UnknownRoleException;
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

    public void updateUser (User targetUser, UpdateProfileRequest request) {

        if (request.getEmail().isPresent())
        {
            if (isEmailUnique(request.getEmail().get()))
            {
                targetUser.setEmail(request.getEmail().get());
            } else {
                throw new EmailNotUniqueException("This email is used by another user");
            }
        }

        if (request.getPhoneNumber().isPresent())
        {
            if (isPhoneNumberUnique(request.getPhoneNumber().get()))
            {
                targetUser.setPhoneNumber(request.getPhoneNumber().get());
            } else {
                throw new PhoneNumberNotUniqueException("This phone number is used by another user");
            }
        }

        request.getName().ifPresent(targetUser::setName);
        request.getGender().ifPresent(targetUser::setGender);

        //Specialist fields
        request.getAddress().ifPresent(((Specialist)targetUser)::setAddress);
        request.getDateOfBirth().ifPresent(((Specialist)targetUser)::setDateOfBirth);
        request.getDisplayImage_path().ifPresent(((Specialist)targetUser)::setDisplayImage_path);

        this.saveUser(targetUser);
    }

    public Long createSpecialistIdNumberPart() {
        return userRepository.countByType(Specialist.class) + 1;
    }
}

package com.na7ki.backend.domain.user.service;

import com.na7ki.backend.account_management.dto.request.UpdateProfileRequest;
import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.common.util.DateUtils;
import com.na7ki.backend.domain.user.exception.EmailNotUniqueException;
import com.na7ki.backend.domain.user.exception.PhoneNumberNotUniqueException;
import com.na7ki.backend.domain.user.exception.EmailNotAssociatedWithAnyAccountException;
import com.na7ki.backend.domain.user.exception.UnknownRoleException;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.model.CreateSpecialistData;
import com.na7ki.backend.domain.user.model.SpecialistFieldsManagementInput;
import com.na7ki.backend.domain.user.model.UniqueFields;
import com.na7ki.backend.domain.user.model.UpdateProfileData;
import com.na7ki.backend.domain.user.repository.PatientRepository;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.domain.user.repository.UserRepository;
import com.na7ki.backend.domain.user.util.UserUtilityMapper;
import com.na7ki.backend.domain.user.verification_code.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserService {

    private final VerificationCodeService verificationCodeService;

    private final UserUtilityMapper mapper;

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final PatientRepository patientRepository;

    private final PasswordEncoder passwordEncoder;

    private final static String DELETED_USER_PASSWORD = "DELETED@NA7KI";


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





    public Specialist createSpecialist (CreateSpecialistData data) {

        checkUniqueFields(mapper.toUniqueFields(data));

        Specialist createdSpecialist = mapper.toSpecialist(data);
        manageSpecialistFields(createdSpecialist, mapper.toSpecialistFieldsManagementInput(data));

        saveUser(createdSpecialist);

        return createdSpecialist;
    }

    private void checkUniqueFields (UniqueFields uniqueFields) {
        if (!isEmailUnique(uniqueFields.email())) {
            throw new EmailNotUniqueException("This email is in use by another user");
        }
        if (!isPhoneNumberUnique(uniqueFields.phoneNumber())) {
            throw new PhoneNumberNotUniqueException("This phone numbers is in use by another user");
        }
    }

    private void manageSpecialistFields(Specialist specialist, SpecialistFieldsManagementInput inputFields) {
        updateUserPassword(specialist, inputFields.password());
        specialist.setAge(DateUtils.calculateAge(inputFields.dateOfBirth()));
        specialist.setSpecialistID("SP" + getSpecialistIdNumberPart());
    }

    public void saveUser(User user) {
        switch (user) {
            case Specialist specialist -> specialistRepository.save(specialist);
            case Patient patient       -> patientRepository.save(patient);
            default                    -> throw new UnknownRoleException("Unknown user type: " + user.getClass().getSimpleName());
        }
    }

    public void updateUser (User targetUser, UpdateProfileData data) {

        updateUniqueFieldOrThrow(
                data.email(),
                this::isEmailUnique,
                targetUser::setEmail,
                () -> new EmailNotUniqueException("This email is used by another user")
        );

        updateUniqueFieldOrThrow(
                data.phoneNumber(),
                this::isPhoneNumberUnique,
                targetUser::setPhoneNumber,
                () -> new PhoneNumberNotUniqueException("This phone number is used by another user")
        );

        data.name().ifPresent(targetUser::setName);
        data.gender().ifPresent(targetUser::setGender);
        data.displayImage_path().ifPresent(targetUser::setDisplayImage_path);

        if (targetUser instanceof Specialist specialist) {
            updateSpecialistFields (specialist, data);
        }

        saveUser(targetUser);
    }

    private void updateSpecialistFields (Specialist specialist, UpdateProfileData data) {
        data.address().ifPresent(specialist::setAddress);

        data.dateOfBirth().ifPresent(dateOfBirth -> {
            specialist.setDateOfBirth(dateOfBirth);
            specialist.setAge(DateUtils.calculateAge(dateOfBirth));
        });
    }
    
    public void updateUserPassword (User targetUser, String newPassword) {
        targetUser.setPassword(passwordEncoder.encode(newPassword));
    }

    private <T> void updateUniqueFieldOrThrow(
            Optional<T> newValue,
            Predicate<T> isUnique,
            Consumer<T> setter,
            Supplier<? extends RuntimeException> exceptionSupplier) {

        if (newValue.isPresent()) {
            T value = newValue.get();
            if (isUnique.test(value)) {
                setter.accept(value);
            } else {
                throw exceptionSupplier.get();
            }
        }
    }

    public void softDeleteUser (User user) {

        user.anonymize(getDeletionUserId());

        //updating the password to a non user-specified value would automatically invalidate all JWTs related to this user
        updateUserPassword(user, DELETED_USER_PASSWORD);

        //delete verification codes
        handleRelatedEntitiesOnDeletion(user);

        userRepository.save(user);

    }

    private void handleRelatedEntitiesOnDeletion(User user) {
        verificationCodeService.deleteCode(user);
    }





    public Long getSpecialistIdNumberPart() {
        return userRepository.countByType(Specialist.class) + 1;
    }

    private Long getDeletionUserId() {
        return userRepository.countByIsDeletedTrue() + 1;
    }

}

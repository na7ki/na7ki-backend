package com.na7ki.backend.domain.user.service;

import com.na7ki.backend.common.util.DateUtils;
import com.na7ki.backend.domain.user.exception.EmailNotUniqueException;
import com.na7ki.backend.domain.user.exception.PhoneNumberNotUniqueException;
import com.na7ki.backend.domain.user.exception.EmailNotAssociatedWithAnyAccountException;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.exception.UnknownRoleException;
import com.na7ki.backend.domain.user.model.create_patient.CreatePatientData;
import com.na7ki.backend.domain.user.model.create_specialist.CreateSpecialistData;
import com.na7ki.backend.domain.user.model.create_specialist.SpecialistFieldsManagementInput;
import com.na7ki.backend.domain.user.model.UniqueFields;
import com.na7ki.backend.domain.user.model.updatable_profile_data.UpdatablePatientData;
import com.na7ki.backend.domain.user.model.updatable_profile_data.UpdatableSpecialistData;
import com.na7ki.backend.domain.user.model.updatable_profile_data.UpdatableUserData;
import com.na7ki.backend.domain.user.repository.PatientRepository;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.domain.user.repository.UserRepository;
import com.na7ki.backend.domain.user.util.PasswordGenerator;
import com.na7ki.backend.domain.user.util.UserMapper;
import com.na7ki.backend.domain.user.verification_code.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserService {

    private final VerificationCodeService verificationCodeService;

    private final UserMapper mapper;

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final PatientRepository patientRepository;

    private final PasswordGenerator passwordGenerator;

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





    public Specialist createSpecialist(CreateSpecialistData data) {
        return createUser(
                mapper.toUniqueFields(data),
                () -> mapper.toSpecialist(data),
                specialist -> manageSpecialistFields(specialist, mapper.toSpecialistFieldsManagementInput(data))
        );
    }

    public Pair<Patient, String> createPatient(CreatePatientData data, Specialist supervisor) {
        String[] passwordHolder = new String[1]; // mutable container to capture value from lambda

        Patient patient = createUser(
                mapper.toUniqueFields(data),
                () -> mapper.toPatient(data),
                p -> passwordHolder[0] = managePatientFields(p, supervisor)
        );

        return Pair.of(patient, passwordHolder[0]);
    }

    private <T extends User> T createUser(
            UniqueFields uniqueFields,
            Supplier<T> entityFactory,
            Consumer<T> fieldManager) {

        checkUniqueFields(uniqueFields);
        T user = entityFactory.get();
        fieldManager.accept(user);
        saveUser(user);
        return user;
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

    private String managePatientFields(Patient patient, Specialist supervisor) {
        String generatedRawPassword = passwordGenerator.generateRandomRawPassword();
        updateUserPassword(patient, generatedRawPassword);
        patient.setPatientID("PT" + getPatientIdNumberPart());
        patient.setSupervisor(supervisor);
        return generatedRawPassword;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void updateUser (User targetUser, UpdatableUserData data) {

        updateUniqueFieldOrThrow(
                data.getEmail(),
                this::isEmailUnique,
                targetUser::setEmail,
                () -> new EmailNotUniqueException("This email is used by another user")
        );

        updateUniqueFieldOrThrow(
                data.getPhoneNumber(),
                this::isPhoneNumberUnique,
                targetUser::setPhoneNumber,
                () -> new PhoneNumberNotUniqueException("This phone number is used by another user")
        );

        data.getName().ifPresent(targetUser::setName);
        data.getGender().ifPresent(targetUser::setGender);
        data.getDisplayImage_path().ifPresent(targetUser::setDisplayImage_path);

        if (targetUser instanceof Specialist specialist) {
            updateSpecialistFields (specialist, ((UpdatableSpecialistData) data));
        } else if (targetUser instanceof Patient patient) {
            updatePatientFields(patient, ((UpdatablePatientData) data));
        } else {
            throw new UnknownRoleException("Unknown role");
        }

        saveUser(targetUser);
    }

    private void updateSpecialistFields (Specialist specialist, UpdatableSpecialistData data) {
        data.getAddress().ifPresent(specialist::setAddress);

        data.getDateOfBirth().ifPresent(dateOfBirth -> {
            specialist.setDateOfBirth(dateOfBirth);
            specialist.setAge(DateUtils.calculateAge(dateOfBirth));
        });
    }

    private void updatePatientFields (Patient patient, UpdatablePatientData data) {
        data.getAge().ifPresent(patient::setAge);
    }
    
    public void updateUserPassword (User targetUser, String newPassword) {
        targetUser.setPassword(passwordGenerator.encodePassword(newPassword));
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

    public Long getPatientIdNumberPart() {
        return userRepository.countByType(Patient.class) + 1;
    }

    private Long getDeletionUserId() {
        return userRepository.countByIsDeletedTrue() + 1;
    }

}

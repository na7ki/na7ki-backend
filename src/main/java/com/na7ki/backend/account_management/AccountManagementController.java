package com.na7ki.backend.account_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.na7ki.backend.account_management.dto.request.UpdatePatientProfileRequest;
import com.na7ki.backend.account_management.dto.request.UpdateSpecialistProfileRequest;
import com.na7ki.backend.account_management.dto.request.UpdateUserProfileRequest;
import com.na7ki.backend.account_management.dto.response.GetUserProfileResponse;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountManagementController {

    private final AccountManagementService accountManagementService;

    private final ObjectMapper objectMapper;

    private final Validator validator;





    @GetMapping("/profile")
    public ResponseEntity<GetUserProfileResponse> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(accountManagementService.getUserProfile(user));
    }

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(
            @AuthenticationPrincipal User targetUser,
            @RequestBody Map<String, Object> rawPayload
    ) {
        UpdateUserProfileRequest data;

        if (targetUser instanceof Specialist) {
            data = objectMapper.convertValue(rawPayload, UpdateSpecialistProfileRequest.class);
        } else if (targetUser instanceof Patient) {
            data = objectMapper.convertValue(rawPayload, UpdatePatientProfileRequest.class);
        } else {
            data = objectMapper.convertValue(rawPayload, UpdateUserProfileRequest.class);
        }

        Set<ConstraintViolation<UpdateUserProfileRequest>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        accountManagementService.updateProfile(targetUser, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal User user) {
        accountManagementService.deleteAccount(user);
        return ResponseEntity.noContent().build();
    }

}

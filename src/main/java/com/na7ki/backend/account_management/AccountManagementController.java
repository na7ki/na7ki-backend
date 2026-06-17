package com.na7ki.backend.account_management;

import com.na7ki.backend.account_management.dto.request.UpdateProfileRequest;
import com.na7ki.backend.account_management.dto.response.GetUserProfileResponse;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.exception.EmailNotUniqueException;
import com.na7ki.backend.domain.user.exception.PhoneNumberNotUniqueException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountManagementController {

    private final AccountManagementService accountManagementService;





    @GetMapping("/profile")
    public ResponseEntity<GetUserProfileResponse> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(accountManagementService.getUserProfile(user));
    }

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile (
            @RequestBody @Valid UpdateProfileRequest request,
            @AuthenticationPrincipal User user
    ) {
        accountManagementService.updateProfile(user, request);
        return ResponseEntity.noContent().build();
    }





    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<String> handleEmailNotUnique(EmailNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberNotUniqueException.class)
    public ResponseEntity<String> handlePhoneNoNotUnique(PhoneNumberNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}

package com.na7ki.backend.account_management;

import com.na7ki.backend.account_management.dto.request.UpdateProfileRequest;
import com.na7ki.backend.account_management.dto.response.GetUserProfileResponse;
import com.na7ki.backend.domain.user.entity.User;
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

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal User user) {
        accountManagementService.deleteAccount(user);
        return ResponseEntity.noContent().build();
    }

}

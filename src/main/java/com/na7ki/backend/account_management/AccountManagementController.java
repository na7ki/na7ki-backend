package com.na7ki.backend.account_management;

import com.na7ki.backend.account_management.dto.UpdateProfileRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountManagementController {

    private final AccountManagementService accountManagementService;

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile (
            @RequestBody @Valid UpdateProfileRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        accountManagementService.updateProfile(userDetails.getUsername(), request);
        return ResponseEntity.noContent().build();
    }


}

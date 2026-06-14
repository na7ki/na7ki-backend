package com.na7ki.backend.account_management;

import com.na7ki.backend.account_management.dto.UpdateProfileRequest;
import com.na7ki.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManagementService {

    private final UserService userService;

    public void updateProfile (String username, UpdateProfileRequest request) {
        userService.updateUser(username, request);
    }

}

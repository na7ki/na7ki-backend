package com.na7ki.backend.account_management;

import com.na7ki.backend.account_management.dto.UpdateProfileRequest;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManagementService {

    private final UserService userService;

    public void updateProfile (User targetUser, UpdateProfileRequest request) {
        userService.updateUser(targetUser, request);
    }

}

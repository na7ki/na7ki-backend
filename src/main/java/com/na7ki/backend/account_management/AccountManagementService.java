package com.na7ki.backend.account_management;

import com.na7ki.backend.account_management.dto.request.UpdateProfileRequest;
import com.na7ki.backend.account_management.dto.response.GetUserProfileResponse;
import com.na7ki.backend.account_management.util.ProfileMapper;
import com.na7ki.backend.domain.user.service.UserService;
import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManagementService {

    private final UserService userService;
    private final ProfileMapper mapper;





    public GetUserProfileResponse getUserProfile(User targetUser) {
        return mapper.toResponse(targetUser);
    }

    public void updateProfile (User targetUser, UpdateProfileRequest request) {
        userService.updateUser(targetUser, mapper.toUpdateProfileData(request));
    }

    public void deleteAccount(User targetUser) {
        userService.softDeleteUser(targetUser);
    }

}

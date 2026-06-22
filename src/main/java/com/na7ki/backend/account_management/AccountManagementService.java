package com.na7ki.backend.account_management;

import com.na7ki.backend.account_management.contact_request.ContactRequestService;
import com.na7ki.backend.account_management.contact_request.dto.ContactUsRequest;
import com.na7ki.backend.account_management.dto.request.UpdateProfileRequest;
import com.na7ki.backend.account_management.dto.response.GetUserProfileResponse;
import com.na7ki.backend.account_management.util.ProfileMapper;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManagementService {

    private final UserService userService;
    private final ProfileMapper profileMapper;
    private final ContactRequestService contactRequestService;





    public GetUserProfileResponse getUserProfile(User targetUser) {
        return profileMapper.toResponse(targetUser);
    }

    public void updateProfile (User targetUser, UpdateProfileRequest request) {
        userService.updateUser(targetUser, request);
    }

    public void submitContactRequest (ContactUsRequest request, User associatedUser) {
        contactRequestService.createContactRequest(request.userMessage(), associatedUser);
    }

}

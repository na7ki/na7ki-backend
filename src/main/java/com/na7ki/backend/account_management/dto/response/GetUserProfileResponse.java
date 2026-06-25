package com.na7ki.backend.account_management.dto.response;

import com.na7ki.backend.domain.user.entity.enums.Gender;
import lombok.Data;

@Data
public class GetUserProfileResponse {

    protected String name;
    protected String email;
    protected String phoneNumber;
    protected Gender gender;
    protected String displayImage_path;

}

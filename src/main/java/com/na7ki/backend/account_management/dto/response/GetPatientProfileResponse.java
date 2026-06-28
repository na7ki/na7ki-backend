package com.na7ki.backend.account_management.dto.response;

import lombok.Data;

@Data
public final class GetPatientProfileResponse extends GetUserProfileResponse {

    private Byte age;

}

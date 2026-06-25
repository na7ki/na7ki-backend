package com.na7ki.backend.account_management.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public final class GetSpecialistProfileResponse extends GetUserProfileResponse {

    private String address;
    private LocalDate dateOfBirth;

}

package com.na7ki.backend.auth.dto.request.register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRegisterRequest extends BaseRegisterRequest {

    private String medicalHistory;

}

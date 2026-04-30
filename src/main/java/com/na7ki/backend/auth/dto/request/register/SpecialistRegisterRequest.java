package com.na7ki.backend.auth.dto.request.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class SpecialistRegisterRequest extends BaseRegisterRequest{

    @NotBlank(message = "Specialist must provide their educational degree details")
    private String educationalDegreeDetails;

    @NotNull
    @Size(min = 1, message = "Specialist must upload at least one personal image")
    private List<
            @NotBlank
            String
            >
    personalImages_paths;

}

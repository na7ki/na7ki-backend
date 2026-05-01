package com.na7ki.backend.auth.dto.request.register;

import com.na7ki.backend.core.validation.duplication.NoDuplicates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecialistRegisterRequest extends BaseRegisterRequest{

    @NotBlank(message = "Specialist must provide their educational degree details")
    private String educationalDegreeDetails;

    @NotNull
    @Size(min = 1, message = "Specialist must upload at least one personal image")
    @NoDuplicates(message = "Personal Images must not contain duplicates")
    private List<
            @NotBlank
            String
            >
    personalImages_paths;

}

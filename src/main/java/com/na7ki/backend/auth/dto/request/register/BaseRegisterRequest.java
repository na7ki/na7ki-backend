package com.na7ki.backend.auth.dto.request.register;

import com.na7ki.backend.auth.entity.auxiliary.Gender;
import com.na7ki.backend.auth.dto.request.register.auxiliary.AddressRequest;
import com.na7ki.backend.core.validation.duplication.NoDuplicates;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BaseRegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 12, message = "password must be between 5 and 12 characters")
    @Pattern(regexp = "^\\s*(?=.*[a-zA-Z])(?=.*\\d)\\S+\\s*$", message = "password must contain at least one alphabetical character, at least one number, and no white spaces at the middle")
    private String password;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private Date dateOfBirth;

    @NotNull(message = "Age is required")
    private Byte age;

    @NotNull
    @Size(min = 1, message = "At least one phone number is required")
    @NoDuplicates(message = "Phone numbers must not contain duplicates")
    private List<
            @NotBlank
            @Pattern(regexp = "^\\s*(\\+2)?01\\d{9}\\s*$", message = "Invalid phone number format")
            String
            >
    phoneNumbers;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Valid
    private AddressRequest address;

    //should the path be sent in the request? Or the image id from a number of static images? Or something else?
    @NotBlank(message = "A display image must be chosen")
    private String displayImage_path;

}

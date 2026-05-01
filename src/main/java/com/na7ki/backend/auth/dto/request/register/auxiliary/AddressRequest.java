package com.na7ki.backend.auth.dto.request.register.auxiliary;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(

    @NotBlank(message = "city of residence is required")
    String city,

    String street,

    Short apartmentNo

) {
}

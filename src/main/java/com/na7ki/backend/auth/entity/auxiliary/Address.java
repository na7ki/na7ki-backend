package com.na7ki.backend.auth.entity.auxiliary;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Address(
    @Column(nullable = false, length = 30)
    String city,

    @Column(length = 100)
    String street,

    @Column(name = "appartment_no")
    short apartmentNumber
) {}


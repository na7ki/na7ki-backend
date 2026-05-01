package com.na7ki.backend.core.validation.duplication;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoDuplicatesValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoDuplicates {
    String message() default "List must not contain duplicate values";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

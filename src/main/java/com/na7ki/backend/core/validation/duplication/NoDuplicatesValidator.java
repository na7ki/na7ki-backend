package com.na7ki.backend.core.validation.duplication;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NoDuplicatesValidator implements ConstraintValidator<NoDuplicates, List<?>> {

    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
        if (list == null) return true; // let @NotNull handle null case
        return list.stream().distinct().count() == list.size();
    }
}

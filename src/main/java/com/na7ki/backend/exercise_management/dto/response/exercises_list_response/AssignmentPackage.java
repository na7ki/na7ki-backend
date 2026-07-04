package com.na7ki.backend.exercise_management.dto.response.exercises_list_response;

import lombok.Data;

@Data
public class AssignmentPackage {

    protected Long id;
    protected String name;
    protected String description;

    public static AssignmentPackage copyBase(AssignmentPackage source, AssignmentPackage target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        return target;
    }

}

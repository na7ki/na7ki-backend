package com.na7ki.backend.exercise_management.dto.response.exercises_list_response;

import lombok.Data;

@Data
public class AssignmentPackage {

    protected Long id;
    protected String name;
    protected String description;
    protected String coverImage_url;

    public static void copyBase(AssignmentPackage source, AssignmentPackage target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setCoverImage_url(source.getCoverImage_url());
    }

}

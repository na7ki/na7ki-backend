package com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request;

import lombok.Data;

@Data
public class Package {

    protected Long id;
    protected String name;
    protected String description;

    public static Package copyBase(Package source, Package target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        return target;
    }

}

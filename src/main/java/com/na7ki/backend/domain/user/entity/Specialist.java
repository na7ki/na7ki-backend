package com.na7ki.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("SPECIALIST")
@Data
public class Specialist extends User {

    @Column(name="specialist_id", nullable = false, unique=true, length=15)
    private String specialistId;

    @Column (name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column (nullable = false, length = 300)
    private String address;

    @ElementCollection
    @CollectionTable (name = "specialist-personal_image", joinColumns = @JoinColumn(name = "user_id"))
    @Column (name = "personal_image_path", length = 100)
    private List<String> personalImages_paths;





    @OneToMany(mappedBy = "supervisor", fetch = FetchType.LAZY)
    private List<Patient> patients = new ArrayList<>();





    @Override
    public void anonymize(Long deletionUserId) {
        super.anonymize(deletionUserId);

        //delete all personally identifying data
        this.setAddress("");
        this.setDateOfBirth(LocalDate.of(1700, 1, 1));
        this.setPersonalImages_paths(new ArrayList<>());

        //change specific id
        this.setSpecialistId(this.getSpecialistId() + "_DL");
    }

}

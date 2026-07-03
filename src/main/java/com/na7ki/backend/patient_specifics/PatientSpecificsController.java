package com.na7ki.backend.patient_specifics;

import com.na7ki.backend.patient_specifics.dto.response.SpecialistCatalogData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/specialists")
@RequiredArgsConstructor
public class PatientSpecificsController {

    private final PatientSpecificsService patientSpecificsService;





    @GetMapping
    public ResponseEntity <List<SpecialistCatalogData>> browseSpecialists() {
        return ResponseEntity.status(HttpStatus.OK).body(patientSpecificsService.getAllSpecialists());
    }

}

package com.na7ki.backend.specialist_actions.report.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PatientMonthlyReport {
    private String patientName;
    private String patientSpecificId;
    private String diagnosis;
    private LocalDate treatmentStart;

    private List<TaskStats> tasks;
}

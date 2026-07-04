package com.na7ki.backend.specialist_actions.report.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class WeeklyReportResponse {
    private String weekLabel;
    private OffsetDateTime generatedAt;
    private List<PatientWeeklyReport> patients;
}

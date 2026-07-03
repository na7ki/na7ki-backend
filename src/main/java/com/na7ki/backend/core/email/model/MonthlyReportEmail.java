package com.na7ki.backend.core.email.model;

public record MonthlyReportEmail(

        String specialistName,
        String monthLabel,      // e.g. "June 2026"
        String reportBody       // pre-formatted text block per patient

) {
}

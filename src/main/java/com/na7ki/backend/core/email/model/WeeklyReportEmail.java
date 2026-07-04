package com.na7ki.backend.core.email.model;

public record WeeklyReportEmail(

        String specialistName,
        String weekLabel,       // e.g. "30 Jun – 6 Jul 2026"
        String reportBody

) {
}

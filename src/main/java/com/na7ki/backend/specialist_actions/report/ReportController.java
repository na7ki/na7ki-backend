package com.na7ki.backend.specialist_actions.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specialist/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final WeeklyReportService weeklyReportService;

    @PostMapping("/monthly")
    public ResponseEntity<Void> triggerMonthlyReport() {
        reportService.sendMonthlyReports();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/weekly")
    public ResponseEntity<Void> triggerWeeklyReport() {
        weeklyReportService.sendWeeklyReports();
        return ResponseEntity.noContent().build();
    }
}

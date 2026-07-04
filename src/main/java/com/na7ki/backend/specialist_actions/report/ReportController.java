package com.na7ki.backend.specialist_actions.report;

import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.specialist_actions.report.dto.MonthlyReportResponse;
import com.na7ki.backend.specialist_actions.report.dto.WeeklyReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specialist/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final WeeklyReportService weeklyReportService;

    /**
     * Returns the weekly report (previous Mon–Sun) for the authenticated specialist.
     * GET /api/specialist/reports/weekly
     */
    @GetMapping("/weekly")
    public ResponseEntity<WeeklyReportResponse> getWeeklyReport(
            @AuthenticationPrincipal User specialist) {
        WeeklyReportResponse report = weeklyReportService.getReportForSpecialist((Specialist) specialist);
        return ResponseEntity.ok(report);
    }

    /**
     * Returns the monthly report (previous month) for the authenticated specialist.
     * GET /api/specialist/reports/monthly
     */
    @GetMapping("/monthly")
    public ResponseEntity<MonthlyReportResponse> getMonthlyReport(
            @AuthenticationPrincipal User specialist) {
        MonthlyReportResponse report = reportService.getReportForSpecialist((Specialist) specialist);
        return ResponseEntity.ok(report);
    }

    /**
     * Manually fires the weekly report job (email + notification) for the authenticated specialist,
     * bypassing the Monday-8am schedule. For testing/ops use (e.g. resending a missed report).
     * POST /api/specialist/reports/weekly/trigger
     */
    @PostMapping("/weekly/trigger")
    public ResponseEntity<Void> triggerWeeklyReport(@AuthenticationPrincipal User specialist) {
        weeklyReportService.sendWeeklyReportForSpecialist((Specialist) specialist);
        return ResponseEntity.noContent().build();
    }

    /**
     * Manually fires the monthly report job (email + notification) for the authenticated specialist,
     * bypassing the 1st-of-month schedule. For testing/ops use (e.g. resending a missed report).
     * POST /api/specialist/reports/monthly/trigger
     */
    @PostMapping("/monthly/trigger")
    public ResponseEntity<Void> triggerMonthlyReport(@AuthenticationPrincipal User specialist) {
        reportService.sendMonthlyReportForSpecialist((Specialist) specialist);
        return ResponseEntity.noContent().build();
    }
}

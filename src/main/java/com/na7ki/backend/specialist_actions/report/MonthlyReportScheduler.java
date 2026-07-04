package com.na7ki.backend.specialist_actions.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyReportScheduler {

    private final ReportService reportService;

    /**
     * Runs at 08:00 on the 1st of every month.
     * cron = "second minute hour day-of-month month day-of-week"
     */
    @Scheduled(cron = "0 0 8 1 * *")
    public void sendMonthlyReports() {
        log.info("Monthly report job triggered.");
        reportService.sendMonthlyReports();
        log.info("Monthly report job completed.");
    }
}

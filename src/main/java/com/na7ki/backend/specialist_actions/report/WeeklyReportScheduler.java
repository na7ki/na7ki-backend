package com.na7ki.backend.specialist_actions.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeeklyReportScheduler {

    private final WeeklyReportService weeklyReportService;

    /**
     * Runs at 08:00 every Monday — covers the previous Mon–Sun week.
     * cron = "second minute hour day-of-month month day-of-week"
     */
    @Scheduled(cron = "0 0 8 * * MON")
    public void sendWeeklyReports() {
        log.info("Weekly report job triggered.");
        weeklyReportService.sendWeeklyReports();
        log.info("Weekly report job completed.");
    }
}

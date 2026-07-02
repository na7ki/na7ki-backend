package com.na7ki.backend.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.notification.dto.response.NotificationResponse;
import com.na7ki.backend.notification.entity.Notification;
import com.na7ki.backend.notification.entity.enums.NotificationType;
import com.na7ki.backend.notification.exception.NotificationNotFoundException;
import com.na7ki.backend.notification.mapper.NotificationMapper;
import com.na7ki.backend.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final ObjectMapper objectMapper;

    public Notification createNotification(
            User recipient,
            NotificationType type,
            String title,
            String message,
            Long referenceId,
            Map<String, Object> details
    ) {
        Notification notification = Notification.builder()
                .recipient(recipient)
                .type(type)
                .title(title)
                .message(message)
                .referenceId(referenceId)
                .metadata(writeMetadata(details))
                .isRead(false)
                .build();

        return notificationRepository.save(notification);
    }

    private String writeMetadata(Map<String, Object> details) {
        if (details == null || details.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(details);
        } catch (Exception e) {
            return null;
        }
    }

    public void notifyTaskAssigned(Patient patient, Specialist specialist, Long assignmentId, Map<String, Object> details) {
        String message = "%s assigned you new exercises to complete".formatted(specialist.getName());

        createNotification(
                patient,
                NotificationType.NEW_TASK_ASSIGNED,
                "New task assigned",
                message,
                assignmentId,
                details
        );
    }

    public void notifyReportReady(Specialist specialist, Patient patient, Long reportId) {
        String message = "The report for patient %s is ready to view".formatted(patient.getName());

        createNotification(
                specialist,
                NotificationType.REPORT_READY,
                "Report ready",
                message,
                reportId,
                Map.of(
                        "patientId", patient.getPatientID(),
                        "patientName", patient.getName()
                )
        );
    }

    public List<NotificationResponse> getNotificationsForUser(User user) {
        return notificationMapper.toResponseList(
                notificationRepository.findByRecipientOrderByCreatedAtDesc(user)
        );
    }

    public List<NotificationResponse> getUnreadNotificationsForUser(User user) {
        return notificationMapper.toResponseList(
                notificationRepository.findByRecipientAndIsReadFalseOrderByCreatedAtDesc(user)
        );
    }

    public long getUnreadCount(User user) {
        return notificationRepository.countByRecipientAndIsReadFalse(user);
    }

    public void markAsRead(Long notificationId, User user) {
        Notification notification = notificationRepository.findByIdAndRecipient(notificationId, user)
                .orElseThrow(() -> new NotificationNotFoundException("No notification with id " + notificationId + " was found for this user"));

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(User user) {
        List<Notification> unread = notificationRepository.findByRecipientAndIsReadFalseOrderByCreatedAtDesc(user);
        unread.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(unread);
    }

}
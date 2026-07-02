package com.na7ki.backend.notification.dto.response;

import com.na7ki.backend.notification.entity.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.Map;

public record NotificationResponse(

        Long id,
        NotificationType type,
        String title,
        String message,
        Long referenceId,
        Map<String, Object> details,
        Boolean isRead,
        LocalDateTime createdAt

) {
}
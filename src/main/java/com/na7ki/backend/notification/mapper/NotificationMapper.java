package com.na7ki.backend.notification.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.na7ki.backend.notification.dto.response.NotificationResponse;
import com.na7ki.backend.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final ObjectMapper objectMapper;

    public NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getType(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getReferenceId(),
                readMetadata(notification.getMetadata()),
                notification.getIsRead(),
                notification.getCreatedAt()
        );
    }

    public List<NotificationResponse> toResponseList(List<Notification> notifications) {
        return notifications.stream().map(this::toResponse).toList();
    }

    private Map<String, Object> readMetadata(String metadata) {
        if (metadata == null || metadata.isBlank()) {
            return Map.of();
        }
        try {
            return objectMapper.readValue(metadata, new TypeReference<>() {});
        } catch (Exception e) {
            return Map.of();
        }
    }

}
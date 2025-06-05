package com.study;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

enum NotificationType {
    LIKE,
    COMMENT,
    FOLLOW,
}

@Getter
@AllArgsConstructor
public class Notification {
    private String id;
    private Long userId;
    private NotificationType type;
    private Instant createdAt;
    private Instant updatedAt;

}
